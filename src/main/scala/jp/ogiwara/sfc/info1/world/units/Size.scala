package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.world._

case class Size(x: Length, y: Length, z: Length){
  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: Size): Size = Size(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: Size): Size = Size(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Number): Size = Size(x * rhs, y * rhs, z * rhs)
}

object Size{
  def origin: Size = Size(0f.m, 0f.m, 0f.m)
}