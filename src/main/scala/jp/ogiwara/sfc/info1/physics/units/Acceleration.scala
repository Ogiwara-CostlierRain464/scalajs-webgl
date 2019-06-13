package jp.ogiwara.sfc.info1.physics.units

import jp.ogiwara.sfc.info1.math._

case class Acceleration(mPerS2: Number){

  override def toString: String = s"${mPerS2}m/s^2"

}