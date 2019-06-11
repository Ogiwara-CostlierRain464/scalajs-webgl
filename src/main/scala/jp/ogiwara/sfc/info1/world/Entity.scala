package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.render.{Mesh, Position}

/**
  * Entityとは、実体である。
  */
@mutable
abstract class Entity(val id: EntityID,var position: Position){
  def render(): Mesh

  override def toString: String = {
    s"Entity(pos: $position)"
  }
}

case class EntityID(id: Int)

