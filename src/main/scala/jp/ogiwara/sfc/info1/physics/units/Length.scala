package jp.ogiwara.sfc.info1.physics.units

import jp.ogiwara.sfc.info1.math.Number

case class Length(meter: Number){
  def /(rhs: Time): Speed = Speed(meter / rhs.second)

  override def toString: String = s"${meter}m"
}
