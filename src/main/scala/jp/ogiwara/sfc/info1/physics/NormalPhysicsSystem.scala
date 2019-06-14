package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import scala.collection.mutable

@mutable
class NormalPhysicsSystem extends System{

  override def update(state: WorldState): WorldState = {
    val newEntities = state.entities.map { entity =>
      require(entity.metadatas.contains(RigidBody.key))

      val rigidBody = entity.metadatas(RigidBody.key).asInstanceOf[RigidBody]
      val update = rigidBody.step
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
