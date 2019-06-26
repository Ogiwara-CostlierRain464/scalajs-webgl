package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.elements.Static
import jp.ogiwara.sfc.info1.system.physics.units.{Forces, Momentums, Speeds}

/**
  * 外力を加える
  */
object ForcePipeline {
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

    // 並進速度の更新
    var newState = state.copy(
      linearVelocity = state.linearVelocity + (externalForce / attribute.mass * timeStep)
    )

    // 角運動量
    val newAngularMomentum: Momentums = angularMomentum + externalTorque * timeStep

    val newAngularVelocityVec = worldInertiaInverse × newAngularMomentum.vector

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
