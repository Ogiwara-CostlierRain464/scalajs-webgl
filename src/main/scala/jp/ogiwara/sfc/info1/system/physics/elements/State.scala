package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units.Speeds
import jp.ogiwara.sfc.info1.world.Rotation
import jp.ogiwara.sfc.info1.world.units.Position

case class State(
                  // 位置
                  position: Position,
                  // 姿勢
                  orientation: Rotation,
                  // 並進速度

                  var linearVelocity: Speeds,
                  // 回転速度
                  var angularVelocity: Speeds,
                  motionType: MotionType = Active
                )

sealed trait MotionType
// 剛体としてアクティブ
case object Active extends MotionType
// 動きが固定されている
case object Static extends MotionType