package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.mutable

// 拘束
@mutable
case class Constraint(
                       // 拘束軸
                       var axis: Vector3 = Vector3.origin,
                       // 拘束式の分母
                       var jacDiagInv: Number = 0,
                       // 初期拘束力
                       var rhs: Number = 0,
                       // 拘束力の下限
                       var lowerLimit: Number = 0,
                       // 拘束力の上限
                       var upperLimit: Number = 0,
                       // 蓄積される拘束力
                       var accumImpulse: Number = 0
                     )