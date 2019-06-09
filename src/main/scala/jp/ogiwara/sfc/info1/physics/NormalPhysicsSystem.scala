package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.world._

@mutable
class NormalPhysicsSystem extends System{

  /**
    * EntityとRigidBodyを紐付け、update毎にRigidBodyを更新して、Entityに適用する
    */
  val map: Map[EntityID, RigidBody] = Map()

  override def update(state: State): State = {
    // 重力を加えるだけなのもよし！
    state
  }
}
