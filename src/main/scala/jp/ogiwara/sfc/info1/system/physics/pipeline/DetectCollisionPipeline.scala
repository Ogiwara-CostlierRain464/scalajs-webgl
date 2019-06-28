package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics.elements.CollisionPair

object DetectCollisionPipeline {
  def apply(objects: Seq[RigidBody], pairs: Seq[CollisionPair]): Unit ={
    pairs.foreach { pair =>

    }
  }
}
