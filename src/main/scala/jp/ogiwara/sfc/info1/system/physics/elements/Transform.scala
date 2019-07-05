package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Matrix3, Matrix4, Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units.ShapeLocalPosition
import jp.ogiwara.sfc.info1.world.Rotation
import jp.ogiwara.sfc.info1.world.units.Position

/**
  * 座標系の変換行列を表す
  */
case class Transform(matrix: Matrix4){
  def ×(rhs: Transform): Transform =
    Transform(matrix × rhs.matrix)

  def inverse: Transform = Transform(matrix.inverse)

  // Get the upper-left 3x3 submatrix of a 3x4 transformation matrix
  def upper3x3: Matrix3 ={
    val (a,b,c,_,e,f,g,_,i,j,k,_,_,_,_,_) = matrix.value

    Matrix3(
      a,e,i,
      b,f,j,
      c,g,k
    )
  }

  // Get the translation component of a 3x4 transformation matrix
  def translation: Vector3 ={
    val (_,_,_,_,_,_,_,_,_,_,_,_,m,n,o,_) = matrix.value

    Vector3(m,n,o)
  }
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