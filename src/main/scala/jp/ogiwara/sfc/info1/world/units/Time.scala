package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.Number

case class Time(second: Number){
  def +(rhs: Time): Time = Time(second + rhs.second)
  def -(rhs: Time): Time = Time(second - rhs.second)
  def *(rhs: Number): Time = Time(second * rhs)

  override def toString: String = s"${second}s"
}
