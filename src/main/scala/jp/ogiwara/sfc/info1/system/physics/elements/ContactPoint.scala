package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.mutable

// 衝突点
@mutable
case class ContactPoint(
                         // 貫通深度
                         var distance: Number = 0,
                         // 衝突点(剛体Aのローカル座標系)
                         var pointA: Vector3 = Vector3.origin,
                         // 衝突点(剛体Bのローカル座標系)
                         var pointB: Vector3 = Vector3.origin,
                         // 衝突点の法線ベクトル(ワールド座標系)
                         var normal: Vector3 = Vector3.origin,
                         // 拘束
                         constraints: Array[Constraint] = Array(Constraint(), Constraint(), Constraint())
                       ){

  override def toString: String = s"${pointA.toPrettyStr}💥${pointB.toPrettyStr}"

  def reset(): Unit ={
    constraints(0).accumImpulse = 0
    constraints(1).accumImpulse = 0
    constraints(2).accumImpulse = 0
  }
}
