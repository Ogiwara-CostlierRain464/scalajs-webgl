package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.world.units.Time

case class Speed(mPerS: Number){
  def /(rhs: Time): Acceleration = Acceleration(mPerS / rhs.second)

  override def toString: String = s"${mPerS}m/s"

  def *(number: Number): Speed =
    Speed(mPerS * number)

  def -(rhs: Speed): Speed =
    Speed(mPerS - rhs.mPerS)
}
