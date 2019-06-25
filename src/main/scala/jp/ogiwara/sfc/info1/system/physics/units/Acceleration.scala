package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math._

case class Acceleration(mPerS2: Number){

  override def toString: String = s"${mPerS2}m/s^2"

  def unary_-(): Acceleration = Acceleration(-mPerS2)

}