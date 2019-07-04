package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.world.units.Length
import jp.ogiwara.sfc.info1.world._

/**
  * 剛体のローカル座標値
  */
case class RigidBodyLocalPosition(x: Length, y: Length, z: Length){
  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def +(rhs: RigidBodyLocalPosition): RigidBodyLocalPosition = RigidBodyLocalPosition(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: RigidBodyLocalPosition): RigidBodyLocalPosition = RigidBodyLocalPosition(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Number): RigidBodyLocalPosition = RigidBodyLocalPosition(x * rhs, y * rhs, z * rhs)
}

object RigidBodyLocalPosition{
  def origin: RigidBodyLocalPosition = RigidBodyLocalPosition(0f.m, 0f.m, 0f.m)
}