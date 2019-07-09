package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.elements.Static
import jp.ogiwara.sfc.info1.system.physics.units.{Forces, Momentums, Speeds}

/**
  * 外力を加える
  */
object ForcePipeline {

  final val maxLinearVelocity: Number = 340
  final val maxAngularVelocity: Number = Math.PI * 60f


  /**
    * 剛体に外力を与える
    */
  def apply(
             rigidBody: RigidBody,
             externalForce: Forces,
             externalTorque: Forces,
             timeStep: Number
           ): RigidBody ={
    val state = rigidBody.state
    val attribute = rigidBody.attribute

    if(state.motionType == Static) return rigidBody

    // 軸dを元にした回転後の物体の原点に対する慣性テンソルをI'とすると、
    // I' = RIR^t が成り立つ(P111)
    val orientation = state.orientation.asMatrix.asMatrix3

    // I' = RIR^t
    val worldInertia = orientation × attribute.inertia × orientation.transpose
    // = R I^-1 R^t
    val worldInertiaInverse = orientation × attribute.inertia.inverse × orientation.transpose

    // 角運動量 = I' * v
    val angularMomentumVec: Vector3 = worldInertia × state.angularVelocity.vector
    val angularMomentum = Momentums(angularMomentumVec.x.Ns,angularMomentumVec.y.Ns, angularMomentumVec.z.Ns )


    var newLinearVelocity = state.linearVelocity + (externalForce / attribute.mass * timeStep)

    if(newLinearVelocity.vector.norm.sqrt > (maxLinearVelocity ^ 2)){
      val fixed = (newLinearVelocity.vector / newLinearVelocity.vector.norm.sqrt.sqrt) * maxLinearVelocity
      newLinearVelocity = Speeds(fixed.x.mPerS, fixed.y.mPerS, fixed.z.mPerS)
    }

    // 並進速度の更新
    var newState = state.copy(
      linearVelocity = newLinearVelocity
    )

    // 角運動量
    val newAngularMomentum: Momentums = angularMomentum + externalTorque * timeStep

    var newAngularVelocityVec = worldInertiaInverse × newAngularMomentum.vector

    if(newAngularVelocityVec.norm.sqrt > (maxAngularVelocity ^ 2)){
      newAngularVelocityVec = (newAngularVelocityVec / newAngularVelocityVec.norm.sqrt.sqrt) * maxAngularVelocity
    }

    // 角速度の更新
    newState = newState.copy(
      // v = I^-1 * P -> (P/I) = v
      angularVelocity = Speeds(newAngularVelocityVec.x.mPerS, newAngularVelocityVec.y.mPerS, newAngularVelocityVec.z.mPerS)
    )

    rigidBody.copy(
      state = newState
    )
  }
}
