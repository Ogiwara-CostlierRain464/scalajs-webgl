package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.Speeds
import jp.ogiwara.sfc.info1.render.Position

case class State(
                  // 位置
                  position: Position,
                  // 姿勢
                  orientation: Quaternion,
                  // 並進速度
                  linearVelocity: Vector3,
                  // 回転速度
                  angularVelocity: Vector3,
                  motionType: MotionType = Active
                )

sealed trait MotionType
// 剛体としてアクティブ
case object Active extends MotionType
// 動きが固定されている
case object Static extends MotionType