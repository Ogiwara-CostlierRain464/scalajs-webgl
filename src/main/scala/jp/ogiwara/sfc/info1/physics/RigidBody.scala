package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render.Position
import jp.ogiwara.sfc.info1.world.Entity

/**
  * 剛体を表す
  */
case class RigidBody(position: Position,mass: Number,
                     speed: Speeds = new Speeds(Vector3.origin),

                    ){

  def step: RigidBody = {
    // 重力
    val newSpeed = speed + Vector3(0,-0.98,0)
    val newPosition = position + newSpeed.vector

    copy(
      speed = newSpeed,
      position = newPosition
    )
  }

  def applyToEntity(entity: Entity): Entity ={
    entity.position = position

    entity
  }
}
