package jp.ogiwara.sfc.info1.system.physics

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.elements.{Attribute, Collidable, State}
import jp.ogiwara.sfc.info1.system.physics.units.Mass
import jp.ogiwara.sfc.info1.world.{Entity, EntityMeta}

/**
  * 剛体を表す
  *
  * 剛体の性質は三つの要素から構成される
  * - 形状(Collidable)
  * - 状態(位置、姿勢、速度)
  * - 属性(重さ、摩擦、反発)
  */
case class RigidBody(
                     id: RigidBodyId,
                     collidable: Collidable,
                     state: State,
                     attribute: Attribute) extends EntityMeta{

  /**
    * mutableなEntityに、この剛体の情報を反映させる
    */
  def applyToEntity(entity: Entity): Entity ={
    entity.position = state.position
    entity.rotation = state.orientation

    entity
  }
}

object RigidBody{
  final val key = "phy"
}

case class RigidBodyId(value: Int)