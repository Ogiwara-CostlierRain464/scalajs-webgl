package jp.ogiwara.sfc.info1.physics.pipeline

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.math._
import Math._
import jp.ogiwara.sfc.info1.physics.RigidBody
import jp.ogiwara.sfc.info1.physics.elements.Static

object IntegratePipeline {

  /**
    * 剛体に外力を与える
    */
  def apply(
                          rigidBody: RigidBody,
                          externalForce: Vector3,
                          externalTorque: Vector3,
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
    val angularMomentum = worldInertia × state.angularVelocity

    // 並進速度の更新
    var newState = state.copy(
      linearVelocity = externalForce / attribute.mass.kg * timeStep
    )

    // 角運動量
    val newAngularMomentum: Vector3 = angularMomentum + externalTorque * timeStep

    // 角速度の更新
    newState = newState.copy(
      // v = I^-1 * P -> (P/I) = v
      angularVelocity = worldInertiaInverse × newAngularMomentum
    )


    if(newState.angularVelocity.norm > pow(PI * 60,2)){
      newState = newState.copy(
        angularVelocity = newState.angularVelocity.normalized
      )
    }


    rigidBody.copy(
      state = newState
    )
  }
}
