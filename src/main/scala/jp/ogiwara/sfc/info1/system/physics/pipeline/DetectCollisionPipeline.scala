package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.system.physics.{Bodies, RigidBody}
import jp.ogiwara.sfc.info1.system.physics.elements.{CollisionPair, Transform}

object DetectCollisionPipeline {
  def apply(objects: Bodies, pairs: Seq[CollisionPair]): Unit ={
    pairs.foreach { pair =>
      val a = objects.findBy(pair.rigidBodyIndexA).get
      val b = objects.findBy(pair.rigidBodyIndexB).get

      val transformA = Transform.from(a.state.orientation, a.state.position)
      val transformB = Transform.from(b.state.orientation, b.state.position)

      for(
        aShape <- a.collidable.shapes;
        bShape <- b.collidable.shapes
      ){

        //Three types of `position`
      }
    }
  }
}
