package jp.ogiwara.sfc.info1.system.physics.elements

case class Pair(
                 var pairType: PairType,
                 // 衝突情報
                 var contact: Contact,
                 // 剛体AのIndex
                 rigidBodyIndexA: Int,
                 // 剛体BのIndex
                 rigidBodyIndexB: Int,
               ){
  def key: Int = (rigidBodyIndexA * (2 ^ 32)) + rigidBodyIndexB
}

sealed trait PairType
// 新規
case object New extends PairType
// 維持
case object Keep extends PairType