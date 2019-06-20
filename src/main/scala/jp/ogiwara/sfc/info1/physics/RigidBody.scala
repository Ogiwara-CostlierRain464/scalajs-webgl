package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.physics.units.Mass
import jp.ogiwara.sfc.info1.render.Position
import jp.ogiwara.sfc.info1.world.{Entity, EntityMeta}

/**
  * 剛体を表す
  *
  * 剛体の性質は三つの要素から構成される
  * - 形状(Collidable)
  * - 状態(位置、姿勢、速度)
  * - 属性(重さ、摩擦、反発)
  */
case class RigidBody(position: Position, mass: Mass,
                     speed: Speeds = new Speeds(Vector3.origin),
                     accelerations: Accelerations = Accelerations(0f.mPerS2, (-0.98f).mPerS2,0f.mPerS2)
                    ) extends EntityMeta{

  def step: RigidBody = {
    // 一定加速度
    val newSpeed = speed + accelerations.vector
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

object RigidBody{
  final val key = "phy"
}