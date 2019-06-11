package jp.ogiwara.sfc.info1.world

/**
  * 世界の状態を表す
  */
case class WorldState(entities: Seq[Entity]){
  def makeSnapShot: WorldSnapshot ={
    val meshes = entities.map(_.render())

    WorldSnapshot(meshes)
  }
}
