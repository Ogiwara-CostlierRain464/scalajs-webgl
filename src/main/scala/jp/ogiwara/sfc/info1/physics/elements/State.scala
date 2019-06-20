package jp.ogiwara.sfc.info1.physics.elements

import jp.ogiwara.sfc.info1.math.Quaternion
import jp.ogiwara.sfc.info1.physics.Speeds
import jp.ogiwara.sfc.info1.render.Position

case class State(
                  // 位置
                  position: Position,
                  // 姿勢
                  orientation: Quaternion,
                  // 並進速度
                  linearVelocity: Speeds,
                  // 回転速度
                  angularVelocity: Speeds,
                  motionType: MotionType
                )

sealed trait MotionType
// 剛体としてアクティブ
case object Active extends MotionType
// 動きが固定されている
case object Static extends MotionType