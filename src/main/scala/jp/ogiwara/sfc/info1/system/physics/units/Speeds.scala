package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.world.units.{Length, Time}

case class Speeds(x: Speed, y: Speed, z: Speed){
  def vector: Vector3 = Vector3(x.mPerS, y.mPerS, z.mPerS)

  def +(rhs: Speeds): Speeds = Speeds(x + rhs.x, y + rhs.y, z + rhs.z)
  def -(rhs: Speeds): Speeds = Speeds(x - rhs.x, y - rhs.y, z - rhs.z)
  def *(rhs: Time): (Length, Length, Length) = (x * rhs, y * rhs, z * rhs)
}

object Speeds{
  def origin: Speeds = Speeds(0f.mPerS, 0f.mPerS, 0f.mPerS)
}