package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.physics.units.Speed

case class Speeds(x: Speed, y: Speed, z: Speed){
  def this(vector3: Vector3) = this(vector3.x.mPerS,vector3.y.mPerS,vector3.z.mPerS)

  def vector: Vector3 = Vector3(x.mPerS, y.mPerS, z.mPerS)

  def +(rhs: Speeds): Speeds =
    this.+(rhs.vector)

  def +(rhs: Vector3): Speeds =
    new Speeds(vector + rhs)

  def -(rhs: Speeds): Speeds =
    this.-(rhs.vector)

  def -(rhs: Vector3): Speeds =
    new Speeds(vector - rhs)
}
