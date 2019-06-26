package jp.ogiwara.sfc.info1.system.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1._
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.pipeline.{BroadPhasePipeline, ForcePipeline, IntegratePipeline}
import jp.ogiwara.sfc.info1.system.physics.units.Forces

import scala.collection.mutable

@mutable
class NormalPhysicsSystem extends System{

  final val timeStep = 0.016f

  override def update(state: WorldState): WorldState = {


    val newEntities = state.entities.map { entity =>
      require(entity.metadatas.contains(RigidBody.key))
      val rigidBody = entity.metadatas(RigidBody.key).asInstanceOf[RigidBody]

      val update0 = ForcePipeline(rigidBody, Gravity * rigidBody.attribute.mass, Forces(0f.N,0f.N,0f.N), timeStep)
      val update = IntegratePipeline(update0, 0.016f)

      update.applyToEntity(entity)

      entity.metadatas(RigidBody.key) = update
      entity
    }

    val newState = state.copy(
      entities = newEntities
    )

    newState
  }
}
