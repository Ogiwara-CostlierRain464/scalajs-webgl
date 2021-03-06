package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.world.units.{Length, Time}

case class Speed(mPerS: Number){
  def /(rhs: Time): Acceleration = Acceleration(mPerS / rhs.second)

  override def toString: String = s"${mPerS}m/s"

  def *(number: Number): Speed = Speed(mPerS * number)
  // m/s * s -> m
  def *(second: Time): Length = Length(mPerS * second.second)
  def -(rhs: Speed): Speed = Speed(mPerS - rhs.mPerS)
  def +(rhs: Speed): Speed = Speed(mPerS + rhs.mPerS)
}
