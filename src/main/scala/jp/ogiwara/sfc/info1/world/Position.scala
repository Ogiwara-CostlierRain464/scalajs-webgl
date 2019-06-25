package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.math.{Vector3, _}
import jp.ogiwara.sfc.info1.world.units.Length


case class Position(x: Length,y: Length, z: Length){

  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: Position): Position = Position(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: Position): Position = Position(x + rhs.x, y + rhs.y, z + rhs.z)
  def *(rhs: Number): Position = Position(x * rhs, y * rhs, z * rhs)
}

object Position{
  def origin = Position(0f.m,0f.m,0f.m)
}