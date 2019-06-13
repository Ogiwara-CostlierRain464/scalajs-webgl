package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render.Position

/**
  * 剛体を表す
  */
case class RigidBody(position: Position,mass: Number, speed: Vector3 = Vector3.origin){

  def step: RigidBody = {
    val newSpeed = speed + Vector3(0,-0.98,0)

    copy(
      speed = newSpeed
    )
  }
}
