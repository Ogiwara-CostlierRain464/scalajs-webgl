package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Matrix3, Vector3}
import jp.ogiwara.sfc.info1.world.Rotation
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.mutable

@mutable
case class SolverBody(
                       // 並進速度差分
                       var deltaLinearVelocity: Vector3,
                       // 回転速度差分
                       var deltaAngularVelocity: Vector3,
                       // 姿勢
                       var orientation: Rotation,
                       // 慣性テンソルの逆行列
                       var inertiaInv: Matrix3,
                       // 質量の逆数
                       var massInv: Number
                     )
