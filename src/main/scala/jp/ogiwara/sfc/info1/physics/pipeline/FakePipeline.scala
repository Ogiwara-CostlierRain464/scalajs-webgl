package jp.ogiwara.sfc.info1.physics.pipeline

import jp.ogiwara.sfc.info1.physics.RigidBody

object FakePipeline {
  def apply(rigidBody: RigidBody): RigidBody ={
    val state = rigidBody.state
    val newState = state.copy(
      position = state.position + state.linearVelocity,
      //orientation = state.orientation
    )

    rigidBody.copy(
      state = newState
    )
  }
}
