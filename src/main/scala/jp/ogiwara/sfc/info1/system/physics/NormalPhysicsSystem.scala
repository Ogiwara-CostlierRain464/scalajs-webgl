package jp.ogiwara.sfc.info1.system.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1._
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.elements.CollisionPair
import jp.ogiwara.sfc.info1.system.physics.pipeline._
import jp.ogiwara.sfc.info1.system.physics.units.Forces

import scala.collection.mutable

@mutable
class NormalPhysicsSystem extends System{

  var collisionPairs: Seq[CollisionPair] = Seq()
  final val timeStep = 0.016f

  override def update(state: WorldState): WorldState = {

    val pipe0 = state.entities.map { entity =>
      val rigidBody = entity.metadatas(RigidBody.key).asInstanceOf[RigidBody]

      val update = ForcePipeline(rigidBody, Gravity * rigidBody.attribute.mass , Forces(0f.N,0f.N,0f.N), timeStep)

      entity.metadatas(RigidBody.key) = update
      entity
    }

    val bodies = pipe0.map(_.metadatas(RigidBody.key).asInstanceOf[RigidBody])
    collisionPairs = BroadPhasePipeline.findPair(bodies, collisionPairs)

    val bodies2 = pipe0.map(_.metadatas(RigidBody.key).asInstanceOf[RigidBody])
    // ここで、collisionPairに変更が加わる
    DetectCollisionPipeline(Bodies(bodies2),collisionPairs)

    // ここでは、Bodiesにも変更が加わる
    ConstraintSolverPipeline(Bodies(bodies2), collisionPairs, Seq(), 10, 0.1, 0.0001, timeStep)

    if(collisionPairs.nonEmpty){
      println(collisionPairs)
    }

    val pipe1 = pipe0.map { entity =>
      val rigidBody = entity.metadatas(RigidBody.key).asInstanceOf[RigidBody]

      val update = IntegratePipeline(rigidBody, 0.016f)

      update.applyToEntity(entity)

      entity.metadatas(RigidBody.key) = update
      entity
    }

    //TODO: より直感的にPipelineを適用できるようにしよう


    val newState = state.copy(
      entities = pipe1
    )

    newState
  }
}
