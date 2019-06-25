package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.system.physics.units.{Speed, Time}

case class Length(meter: Number){
  def /(rhs: Time): Speed = Speed(meter / rhs.second)

  override def toString: String = s"${meter}m"
}
