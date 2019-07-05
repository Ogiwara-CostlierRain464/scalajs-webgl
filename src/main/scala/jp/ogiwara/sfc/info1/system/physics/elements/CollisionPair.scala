package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.system.physics.{RigidBody, RigidBodyId}
import Math._

import jp.ogiwara.sfc.info1.lateInit

case class  CollisionPair(
                 @lateInit var pairType: PairType,
                 // 衝突情報
                 @lateInit var contact: Contact,
                 // 剛体AのIndex
                 rigidBodyIndexA: RigidBodyId,
                 // 剛体BのIndex
                 rigidBodyIndexB: RigidBodyId,
               ){

  override def toString: String = {
    val head = if(pairType == New) "n" else "k"

    head + s"($rigidBodyIndexA <- $contact -> $rigidBodyIndexB)"

  }

  override def equals(obj: Any): Boolean = {
    if(!obj.isInstanceOf[CollisionPair]) false
    else {
      val other = obj.asInstanceOf[CollisionPair]
      rigidBodyIndexA == other.rigidBodyIndexA && rigidBodyIndexB == other.rigidBodyIndexB
    }
  }
}

object CollisionPair{
  def from(bodyA: RigidBodyId, bodyB: RigidBodyId): CollisionPair ={
    val a = min(bodyA.value, bodyB.value)
    val b = max(bodyA.value, bodyB.value)

    CollisionPair(
      pairType = null,
      contact = null,
      rigidBodyIndexA = RigidBodyId(a),
      rigidBodyIndexB = RigidBodyId(b)
    )
  }
}

sealed trait PairType
// 新規
case object New extends PairType
// 維持
case object Keep extends PairType