package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.math._
import Math._

import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics.elements.Static
import jp.ogiwara.sfc.info1.world.units.Time

/**
  * 位置を更新する
  */
object IntegratePipeline {
  def apply(body: RigidBody, timeStep: Time): RigidBody = {
    val state = body.state

    val dAng = Quaternion(0, state.angularVelocity.vector.x, state.angularVelocity.vector.y, state.angularVelocity.vector.z) * state.orientation * 0.5

    val newState = state.copy(
      position = state.position + state.linearVelocity * timeStep,
      orientation = (state.orientation + dAng * timeStep.second).normalized
    )

    body.copy(
      state = newState
    )
  }
}
