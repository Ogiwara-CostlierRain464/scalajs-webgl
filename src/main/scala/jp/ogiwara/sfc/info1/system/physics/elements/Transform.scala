package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Matrix4, Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units.ShapeLocalPosition
import jp.ogiwara.sfc.info1.world.Rotation
import jp.ogiwara.sfc.info1.world.units.Position

/**
  * 座標系の変換行列を表す
  */
case class Transform(matrix: Matrix4){
  def ×(rhs: Transform): Transform =
    Transform(matrix × rhs.matrix)

  def inverse: Transform = Transform(matrix.)
}

object Transform{
  def from(rotation: Rotation, position: Position): Transform =
    from(rotation, position.vector)

  def from(rotation: Rotation, position: ShapeLocalPosition): Transform =
    from(rotation, position.vector)

  private def from(rotation: Quaternion, vec: Vector3): Transform ={
    val rhs = Matrix4(
      0,0,0,vec.x,
      0,0,0,vec.y,
      0,0,0,vec.z,
      0,0,0,  0,
    )

    Transform(rotation.asMatrix + rhs)
  }
}