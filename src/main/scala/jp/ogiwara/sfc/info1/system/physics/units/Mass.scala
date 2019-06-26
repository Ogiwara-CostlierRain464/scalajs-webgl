package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Number

case class Mass(kg: Number){
  def +(rhs: Mass): Mass = Mass(kg + rhs.kg)
  def -(rhs: Mass): Mass = Mass(kg - rhs.kg)
  def *(rhs: Number): Mass = Mass(kg * rhs)
  def /(rhs: Acceleration): Force = Force(kg * rhs.mPerS2)

  override def toString: String = s"${kg}kg"
}
