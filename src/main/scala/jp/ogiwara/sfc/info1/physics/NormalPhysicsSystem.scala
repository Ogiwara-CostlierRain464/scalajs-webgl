package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1._
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.physics.pipeline.{BroadPhasePipeline, FakePipeline, ForcePipeline, IntegratePipeline}

import scala.collection.mutable

@mutable
class NormalPhysicsSystem extends System{

  var frame = 0

  override def update(state: WorldState): WorldState = {


    val newEntities = state.entities.map { entity =>
      require(entity.metadatas.contains(RigidBody.key))
      val rigidBody = entity.metadatas(RigidBody.key).asInstanceOf[RigidBody]

      // TODO: Pipeline

      val update = ForcePipeline(rigidBody, Vector3.origin, Vector3(0,1,0), 0.016f)
        .|>(FakePipeline.apply)

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
