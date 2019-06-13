package jp.ogiwara.sfc.info1.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.physics.units.Acceleration

case class Accelerations(x: Acceleration, y: Acceleration, z: Acceleration){
  def this(vector3: Vector3) = this(vector3.x.mPerS2,vector3.y.mPerS2,vector3.z.mPerS2)

  def vector: Vector3 = Vector3(x.mPerS2, y.mPerS2, z.mPerS2)

  def +(rhs: Accelerations): Accelerations =
    this.+(rhs.vector)

  def +(rhs: Vector3): Accelerations =
    new Accelerations(vector + rhs)

  def -(rhs: Accelerations): Accelerations =
    this.-(rhs.vector)

  def -(rhs: Vector3): Accelerations =
    new Accelerations(vector - rhs)
}
