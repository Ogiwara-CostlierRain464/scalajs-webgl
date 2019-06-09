package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.render.Mesh

/**
  * Worldのポリゴンのスナップショット
  */
case class WorldSnapshot(meshes: Seq[Mesh])