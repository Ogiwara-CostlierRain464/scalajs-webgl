package jp.ogiwara.sfc.info1.physics.elements

import jp.ogiwara.sfc.info1.math.Matrix3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.physics.units.Mass

case class Attribute(
                      // 慣性テンソル
                      inertia: Matrix3,
                      mass: Mass,
                      // 反発係数
                      restitution: Number,
                      // 摩擦係数
                      friction: Number
                    )

