package jp.ogiwara.sfc.info1.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number

// 拘束
case class Constraint(
                       // 拘束軸
                       axis: Vector3,
                       // 拘束式の分母
                       jacDiagInv: Number,
                       // 初期拘束力
                       rhs: Number,
                       // 拘束力の下限
                       lowerLimit: Number,
                       // 拘束力の上限
                       upperLimit: Number,
                       // 蓄積される拘束力
                       accumImpulse: Number
                     )