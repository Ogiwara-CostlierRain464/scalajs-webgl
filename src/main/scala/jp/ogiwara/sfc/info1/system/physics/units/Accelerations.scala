package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.world.units.Time

case class Accelerations(x: Acceleration, y: Acceleration, z: Acceleration){

  def vector: Vector3 = Vector3(x.mPerS2, y.mPerS2, z.mPerS2)

  def +(rhs: Accelerations): Accelerations = Accelerations(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: Accelerations): Accelerations = Accelerations(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Time): Speeds = Speeds(x * rhs, y * rhs, z * rhs)
  def *(rhs: Mass): Forces = Forces(x * rhs, y * rhs, z * rhs)

}

