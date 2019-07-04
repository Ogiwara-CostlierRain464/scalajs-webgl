package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.system.physics.{Bodies, RigidBody}
import jp.ogiwara.sfc.info1.system.physics.elements.CollisionPair

object DetectCollisionPipeline {
  def apply(objects: Bodies, pairs: Seq[CollisionPair]): Unit ={
    pairs.foreach { pair =>
      val a = objects.findBy(pair.rigidBodyIndexA).get
      val b = objects.findBy(pair.rigidBodyIndexB).get

    }
  }
}
