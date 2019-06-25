package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.system.physics.RigidBody

object FakePipeline {
  def apply(rigidBody: RigidBody): RigidBody ={
    val state = rigidBody.state

    val newState = state.copy(
      position = state.position + state.linearVelocity,
      orientation = (state.orientation.asMatrix.asMatrix3 × state.angularVelocity).asQuaternion
    )

    rigidBody.copy(
      state = newState
    )
  }
}
