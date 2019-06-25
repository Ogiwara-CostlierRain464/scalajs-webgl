package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.system.physics.units.Length
import jp.ogiwara.sfc.info1.system.physics._


case class Position(x: Length,y: Length, z: Length){

  def vector: Vector3 = Vector3(x.meter, y.meter, z.meter)

  def this(vector3: Vector3) = this(vector3.x.m, vector3.y.m, vector3.z.m)

  def +(rhs: Position): Position =
    this.+(rhs.vector)

  def +(rhs: Vector3): Position =
    new Position(vector + rhs)

  def -(rhs: Position): Position =
    this.-(rhs.vector)

  def -(rhs: Vector3): Position =
    new Position(vector - rhs)
}

object Position{
  def origin = new Position(Vector3.origin)
}