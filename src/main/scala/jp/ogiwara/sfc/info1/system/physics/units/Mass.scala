package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Number

case class Mass(kg: Number){
  def +(rhs: Mass): Mass = Mass(kg + rhs.kg)
  def -(rhs: Mass): Mass = Mass(kg - rhs.kg)
  def *(rhs: Number): Mass = Mass(kg * rhs)

  override def toString: String = s"${kg}kg"
}
