package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.world.units.Length
import jp.ogiwara.sfc.info1.world._

case class LocalPosition(x: Length, y: Length, z: Length){
  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: LocalPosition): LocalPosition = LocalPosition(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: LocalPosition): LocalPosition = LocalPosition(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Number): LocalPosition = LocalPosition(x * rhs, y * rhs, z * rhs)
}

object LocalPosition{
  def origin: LocalPosition = LocalPosition(0f.m, 0f.m, 0f.m)
}
