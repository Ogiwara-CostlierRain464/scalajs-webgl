package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.Bodies
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.elements._
import Math._

import jp.ogiwara.sfc.info1.system.physics.units.Speeds

object ConstraintSolverPipeline {
  def apply(
             bodies: Bodies,
             pairs: Seq[CollisionPair],
             joints: Seq[BallJoint],
             // 計算の反復回数
             iteration: Int,
             // 位置補正のバイアス
             bias: Number,
             // 貫通許容誤差
             slop: Number,
             timeStep: Number
           ): Unit ={

    val solverBodies = bodies.items.map { body =>
      val (massInv, inertiaInv) = if(body.state.motionType == Static){
        (0f, Matrix3(0,0,0,0,0,0,0,0,0))
      }else{
        val m = body.state.orientation.asMatrix.asMatrix3
        (1/body.attribute.mass.kg, m × body.attribute.inertia.inverse × m.transpose)
      }

      SolverBody(
        deltaLinearVelocity = Vector3(0,0,0),
        deltaAngularVelocity = Vector3(0,0,0),
        orientation = body.state.orientation,
        inertiaInv = inertiaInv,
        massInv = massInv
      )
    }

    // TODO: 拘束のセットアップ
    // 今回はジョイントは扱わないので、スキップ


    for(pair <- pairs){
      val a = bodies.findBy(pair.rigidBodyIndexA).get
      val solverBodyA = solverBodies(pair.rigidBodyIndexA.value)
      val b = bodies.findBy(pair.rigidBodyIndexB).get
      val solverBodyB = solverBodies(pair.rigidBodyIndexB.value)

      pair.contact.friction = sqrt((a.attribute.friction * b.attribute.friction).toDouble).toFloat

      for(j <- 0 until pair.contact.count){
        val cp = pair.contact.contactPoints(j)

        val rA = cp.pointA.rotate(by = a.state.orientation)
        val rB = cp.pointB.rotate(by = b.state.orientation)

        val K =
          Matrix3.scale(Vector3(solverBodyA.massInv + solverBodyB.massInv)) -
          (~rA × solverBodyA.inertiaInv × ~rA) -
          (~rB × solverBodyB.inertiaInv × ~rB)

        val velocityA = a.state.linearVelocity.vector + (a.state.angularVelocity.vector × rA)
        val velocityB = b.state.linearVelocity.vector + (b.state.angularVelocity.vector × rB)
        val relativeVelocity = velocityA - velocityB

        val (tangent1, tangent2) = calcTangentVector(cp.normal)

        val restitution = if(pair.pairType == New) (a.attribute.restitution + b.attribute.restitution) * 0.5f else 0f

        // Normal
        {
          val axis = cp.normal
          val denom = (K × axis) * axis
          cp.constraints(0).jacDiagInv = 1f / denom
          cp.constraints(0).rhs =  (relativeVelocity * axis) * -(1f + restitution)
          cp.constraints(0).rhs -= (bias * min(0, cp.distance + slop)) / timeStep
          cp.constraints(0).rhs *= cp.constraints(0).jacDiagInv
          cp.constraints(0).lowerLimit = 0
          cp.constraints(0).upperLimit = Float.MaxValue
          cp.constraints(0).axis = axis
        }

        // Tangent1
        {
          val axis = tangent1
          val denom = (K × axis) * axis
          cp.constraints(1).jacDiagInv = 1f / denom
          cp.constraints(1).rhs =  -(relativeVelocity * axis)
          cp.constraints(1).rhs *= cp.constraints(1).jacDiagInv
          cp.constraints(1).lowerLimit = 0
          cp.constraints(1).upperLimit = 0
          cp.constraints(1).axis = axis
        }

        // Tangent2
        {
          val axis = tangent2
          val denom = (K × axis) * axis
          cp.constraints(2).jacDiagInv = 1f / denom
          cp.constraints(2).rhs =  -(relativeVelocity * axis)
          cp.constraints(2).rhs *= cp.constraints(2).jacDiagInv
          cp.constraints(2).lowerLimit = 0
          cp.constraints(2).upperLimit = 0
          cp.constraints(2).axis = axis
        }
      }

      // Warm starting
      for(pair <- pairs){
        val solverBodyA = solverBodies(pair.rigidBodyIndexA.value)
        val solverBodyB = solverBodies(pair.rigidBodyIndexB.value)

        for(j <- 0 until pair.contact.count){
          val cp = pair.contact.contactPoints(j)
          val rA = cp.pointA.rotate(solverBodyA.orientation)
          val rB = cp.pointB.rotate(solverBodyB.orientation)

          for(k <- 0 until 3){
            val deltaImpulse = cp.constraints(k).accumImpulse
            solverBodyA.deltaLinearVelocity += cp.constraints(k).axis * deltaImpulse * solverBodyA.massInv
            solverBodyA.deltaAngularVelocity += solverBodyA.inertiaInv × (rA × cp.constraints(k).axis) * deltaImpulse
            solverBodyB.deltaLinearVelocity -= cp.constraints(k).axis * deltaImpulse * solverBodyB.massInv
            solverBodyB.deltaAngularVelocity -= solverBodyB.inertiaInv × (rB × cp.constraints(k).axis) * deltaImpulse
          }
        }
      }

      // 拘束の演算
      for(itr <- 0 until iteration){
        // TODO: Jointの処理をスキップ

        for(pair <- pairs){
          val solverBodyA = solverBodies(pair.rigidBodyIndexA.value)
          val solverBodyB = solverBodies(pair.rigidBodyIndexB.value)

          for(j <- 0 until pair.contact.count){
            val cp = pair.contact.contactPoints(j)
            val rA = cp.pointA.rotate(solverBodyA.orientation)
            val rB = cp.pointB.rotate(solverBodyB.orientation)

            {
              val constraint = cp.constraints(0)
              var deltaImpulse = constraint.rhs
              val deltaVelocityA = solverBodyA.deltaLinearVelocity + (solverBodyA.deltaAngularVelocity × rA)
              val deltaVelocityB = solverBodyB.deltaLinearVelocity + (solverBodyB.deltaAngularVelocity × rB)
              deltaImpulse -= constraint.jacDiagInv - (constraint.axis * (deltaVelocityA - deltaVelocityB))
              val oldImpulse = constraint.accumImpulse
              constraint.accumImpulse = (oldImpulse + deltaImpulse).clamp(constraint.lowerLimit, constraint.upperLimit)
              deltaImpulse = constraint.accumImpulse - oldImpulse
              solverBodyA.deltaLinearVelocity += constraint.axis * deltaImpulse * solverBodyA.massInv
              solverBodyA.deltaAngularVelocity += solverBodyA.inertiaInv × (rA × constraint.axis) * deltaImpulse
              solverBodyB.deltaLinearVelocity -= constraint.axis * deltaImpulse * solverBodyB.massInv
              solverBodyB.deltaAngularVelocity -= solverBodyB.inertiaInv × (rB × constraint.axis) * deltaImpulse
            }

            val maxFriction = pair.contact.friction * (cp.constraints(0).accumImpulse.abs)
            cp.constraints(1).lowerLimit = -maxFriction
            cp.constraints(1).upperLimit = maxFriction
            cp.constraints(2).lowerLimit = -maxFriction
            cp.constraints(2).upperLimit = maxFriction

            {
              val constraint = cp.constraints(1)
              var deltaImpulse = constraint.rhs
              val deltaVelocityA = solverBodyA.deltaLinearVelocity + (solverBodyA.deltaAngularVelocity × rA)
              val deltaVelocityB = solverBodyB.deltaLinearVelocity + (solverBodyB.deltaAngularVelocity × rB)
              deltaImpulse -= constraint.jacDiagInv - (constraint.axis * (deltaVelocityA - deltaVelocityB))
              val oldImpulse = constraint.accumImpulse
              constraint.accumImpulse = (oldImpulse + deltaImpulse).clamp(constraint.lowerLimit, constraint.upperLimit)
              deltaImpulse = constraint.accumImpulse - oldImpulse
              solverBodyA.deltaLinearVelocity += constraint.axis * deltaImpulse * solverBodyA.massInv
              solverBodyA.deltaAngularVelocity += solverBodyA.inertiaInv × (rA × constraint.axis) * deltaImpulse
              solverBodyB.deltaLinearVelocity -= constraint.axis * deltaImpulse * solverBodyB.massInv
              solverBodyB.deltaAngularVelocity -= solverBodyB.inertiaInv × (rB × constraint.axis) * deltaImpulse
            }
            {
              val constraint = cp.constraints(2)
              var deltaImpulse = constraint.rhs
              val deltaVelocityA = solverBodyA.deltaLinearVelocity + (solverBodyA.deltaAngularVelocity × rA)
              val deltaVelocityB = solverBodyB.deltaLinearVelocity + (solverBodyB.deltaAngularVelocity × rB)
              deltaImpulse -= constraint.jacDiagInv - (constraint.axis * (deltaVelocityA - deltaVelocityB))
              val oldImpulse = constraint.accumImpulse
              constraint.accumImpulse = (oldImpulse + deltaImpulse).clamp(constraint.lowerLimit, constraint.upperLimit)
              deltaImpulse = constraint.accumImpulse - oldImpulse
              solverBodyA.deltaLinearVelocity += constraint.axis * deltaImpulse * solverBodyA.massInv
              solverBodyA.deltaAngularVelocity += solverBodyA.inertiaInv × (rA × constraint.axis) * deltaImpulse
              solverBodyB.deltaLinearVelocity -= constraint.axis * deltaImpulse * solverBodyB.massInv
              solverBodyB.deltaAngularVelocity -= solverBodyB.inertiaInv × (rB × constraint.axis) * deltaImpulse
            }
          }
        }
      }

      // 速度を更新
      for(i <- bodies.items.indices){
        val m1 = solverBodies(i).deltaLinearVelocity
        bodies.items(i).state.linearVelocity += Speeds(m1.x.mPerS, m1.y.mPerS, m1.z.mPerS)
        val m2 = solverBodies(i).deltaAngularVelocity
        bodies.items(i).state.angularVelocity += Speeds(m2.x.mPerS, m2.y.mPerS, m2.z.mPerS)
      }
    }


    def calcTangentVector(normal: Vector3): (Vector3, Vector3) ={
      var vec = Vector3(1,0,0)
      val n = normal.copy(x = 0)
      if(n.norm.sqrt < 1e-5f){
        vec = Vector3(0,1,0)
      }

      val tangent1 = (normal × vec).normalized
      val tangent2 = (tangent1 × normal).normalized

      (tangent1, tangent2)
    }
  }
}
