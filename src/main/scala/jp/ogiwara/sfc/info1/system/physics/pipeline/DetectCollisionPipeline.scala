package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.system.physics.{Bodies, RigidBody}
import jp.ogiwara.sfc.info1.system.physics.elements.{CollisionPair, Transform}
import jp.ogiwara.sfc.info1.system.physics.collision._

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
        val offsetTransformA = Transform.from(aShape.offsetRotation, aShape.offsetPosition)
        val worldTransformA = transformA × offsetTransformA

        val offsetTransformB = Transform.from(bShape.offsetRotation, bShape.offsetPosition)
        val worldTransformB = transformB × offsetTransformB

        convexContact(
          aShape.mesh,
          worldTransformA,
          bShape.mesh,
          worldTransformB).foreach { case (normal, penetrationDepth, contactPointA, contactPointB) =>
            if(penetrationDepth < 0){
              pair.contact.addContact(
                penetrationDepth,
                normal,
                offsetTransformA.translation + (offsetTransformA.upper3x3 × contactPointA),
                offsetTransformB.translation + (offsetTransformB.upper3x3 × contactPointB),
              )
            }
        }
      }
    }
  }
}
