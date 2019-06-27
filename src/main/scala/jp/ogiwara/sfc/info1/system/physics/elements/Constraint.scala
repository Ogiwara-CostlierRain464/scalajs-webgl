package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number

// 拘束
case class Constraint(
                       // 拘束軸
                       axis: Vector3 = Vector3.origin,
                       // 拘束式の分母
                       jacDiagInv: Number = 0,
                       // 初期拘束力
                       rhs: Number = 0,
                       // 拘束力の下限
                       lowerLimit: Number = 0,
                       // 拘束力の上限
                       upperLimit: Number = 0,
                       // 蓄積される拘束力
                       accumImpulse: Number = 0
                     )