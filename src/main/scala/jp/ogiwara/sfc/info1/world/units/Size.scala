package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.{Number, Vector3}

case class Size(x: Length, y: Length, z: Length){
  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: Size): Size = Size(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: Size): Size = Size(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Number): Size = Size(x * rhs, y * rhs, z * rhs)
}