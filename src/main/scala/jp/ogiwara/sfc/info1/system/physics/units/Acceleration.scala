package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math._

case class Acceleration(mPerS2: Number){
  def +(rhs: Acceleration): Acceleration = Acceleration(mPerS2 + rhs.mPerS2)
  def -(rhs: Acceleration): Acceleration = Acceleration(mPerS2 - rhs.mPerS2)
  def *(rhs: Number): Acceleration = Acceleration(mPerS2 * rhs)

  override def toString: String = s"${mPerS2}m/s^2"

  def unary_-(): Acceleration = Acceleration(-mPerS2)

}