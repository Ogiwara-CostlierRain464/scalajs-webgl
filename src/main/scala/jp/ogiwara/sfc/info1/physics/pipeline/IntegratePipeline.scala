package jp.ogiwara.sfc.info1.physics.pipeline

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.math._
import Math._
import jp.ogiwara.sfc.info1.physics.RigidBody
import jp.ogiwara.sfc.info1.physics.elements.Static

/**
  * 位置を更新する
  */
object IntegratePipeline {
  def apply(bodies: Seq[RigidBody], timeStep: Number): Seq[RigidBody] = bodies.map { body =>
    val state = body.state

    val dAng = Quaternion(0, state.angularVelocity.x, state.angularVelocity.y, state.angularVelocity.z) * state.orientation * 0.5

    val newState = state.copy(
      position = state.position + state.linearVelocity * timeStep,
      orientation = (state.orientation + dAng * timeStep).normalized
    )

    body.copy(
      state = newState
    )
  }
}
