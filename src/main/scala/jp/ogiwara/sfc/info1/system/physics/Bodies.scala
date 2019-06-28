package jp.ogiwara.sfc.info1.system.physics

/**
  * first collection object of RigidBody
  */
case class Bodies(items: Seq[RigidBody]){
  def findBy(id: RigidBodyId): Option[RigidBody] = items.find(_.id == id)
}
