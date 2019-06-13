package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import scala.collection.mutable

@mutable
class NormalPhysicsSystem extends System{

  /**
    * EntityとRigidBodyを紐付け、update毎にRigidBodyを更新して、Entityに適用する
    */
  var map: mutable.Map[EntityID, RigidBody] = mutable.Map()

  override def update(state: WorldState): WorldState = {
    // 重力を加えるだけなのもよし！
    val newEntities = state.entities.map { entity =>
      if(!map.contains(entity.id)){
        map += (entity.id -> RigidBody(entity.position, 10))
      }

      val rigidBody = map(entity.id)

      val update = rigidBody.step

      map(entity.id) = update

      entity.position = entity.position.vector + update.speed

      entity
    }

    val newState = state.copy(
      entities = newEntities
    )

    newState
  }
}
