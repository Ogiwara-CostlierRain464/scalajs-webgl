package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.world.units.Time
import jp.ogiwara.sfc.info1.system.physics._

case class Forces(x: Force, y: Force, z: Force){
  def /(rhs: Mass): Accelerations = Accelerations(x / rhs, y / rhs, z / rhs)
  def *(rhs: Time): Momentums = Momentums(x * rhs, y * rhs, z * rhs)
}

object Forces{
  def origin: Forces = Forces(0f.N, 0f.N, 0f.N)
}