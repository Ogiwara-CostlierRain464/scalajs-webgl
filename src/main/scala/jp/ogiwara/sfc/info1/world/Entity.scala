package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.render.{Mesh, Position}

/**
  * Entityとは、実体である。
  */
abstract class Entity(val id: EntityID,val position: Position){
  def render(): Mesh
}

case class EntityID(id: Int)

