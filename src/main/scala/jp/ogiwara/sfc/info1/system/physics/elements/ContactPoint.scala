package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Number, Vector3}

// 衝突点
case class ContactPoint(
                         // 貫通深度
                         distance: Number = 0,
                         // 衝突点(剛体Aのローカル座標系)
                         pointA: Vector3 = Vector3.origin,
                         // 衝突点(剛体Bのローカル座標系)
                         pointB: Vector3 = Vector3.origin,
                         // 衝突点の法線ベクトル(ワールド座標系)
                         normal: Vector3 = Vector3.origin,
                         // 拘束
                         constraints: (Constraint,Constraint,Constraint) = (Constraint(),Constraint(),Constraint())
                       )
