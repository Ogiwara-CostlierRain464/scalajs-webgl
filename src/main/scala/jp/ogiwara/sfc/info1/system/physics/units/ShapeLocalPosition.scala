package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.world.units.Length
import jp.ogiwara.sfc.info1.world._

/**
  * 凸形状のローカル座標
  */
case class ShapeLocalPosition(x: Length, y: Length, z: Length) {
  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: ShapeLocalPosition): ShapeLocalPosition = ShapeLocalPosition(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: ShapeLocalPosition): ShapeLocalPosition = ShapeLocalPosition(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Number): ShapeLocalPosition = ShapeLocalPosition(x * rhs, y * rhs, z * rhs)

}

object ShapeLocalPosition{
  def origin: ShapeLocalPosition = ShapeLocalPosition(0f.m, 0f.m, 0f.m)
}
