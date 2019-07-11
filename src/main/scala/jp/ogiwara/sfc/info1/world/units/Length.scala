package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics.units.Speed

case class Length(meter: Number){
  def +(rhs: Length): Length = Length(meter + rhs.meter)
  def -(rhs: Length): Length = Length(meter - rhs.meter)
  def /(rhs: Time): Speed = Speed(meter / rhs.second)
  def /(rhs: Number): Length = Length(meter / rhs)
  def *(rhs: Number): Length = Length(meter * rhs)
  def ^(exp: Number): Length = Length(meter ^ exp)
  def unary_-(): Length = Length(-meter)

  override def toString: String = s"${meter}m"
}
