package jp.ogiwara.sfc.info1.physics.units

import jp.ogiwara.sfc.info1.math._

case class Speed(mPerS: Number){
  def /(rhs: Time): Acceleration = Acceleration(mPerS / rhs.second)

  override def toString: String = s"${mPerS}m/s"

  def *(number: Number): Speed =
    Speed(mPerS * number)

  def -(rhs: Speed): Speed =
    Speed(mPerS - rhs.mPerS)
}
