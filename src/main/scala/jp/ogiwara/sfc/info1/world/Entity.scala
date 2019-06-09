package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.render.Mesh

/**
  * Entityとは、実体である。
  */
abstract class Entity {
  abstract def render(): Mesh
}

case class EntityID(id: Int)
