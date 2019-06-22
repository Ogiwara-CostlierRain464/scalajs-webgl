package jp.ogiwara.sfc.info1.math

import Math._

/**
  * 四元数は回転軸(Vector)と回転角(Scalar)よりなる4成分で3次元空間の回転を表す
  *
  * q
  * = w + (x,y,z)
  * = w + xi + yj + zk
  *
  * a・b (スカラ積)(内積)
  * a×b (ベクトル積)(外積)
  * より、
  * ab = -a・b + a×b (ハミルトン積)
  * を定義する
  *
  * この定義により二つのべクトルの積はスカラー部とベクトル部よりなる拡張された
  * 数になっている。この拡張された数を四元数という
  */
case class Quaternion(w: Number, x: Number, y: Number, z: Number){

  //　共役数
  def inverse: Quaternion = Quaternion(w, -x, -y, -z)

  def norm: Number =
    sqrt(pow(w,2) + pow(x,2) + pow(y,2) + pow(z, 2))

  def +(rhs: Quaternion): Quaternion ={
    Quaternion(
      w + rhs.w,
      x + rhs.x,
      y + rhs.y,
      z + rhs.z,
    )
  }

  def * (rhs: Quaternion): Quaternion ={
    /**
      *
      * q * q2
      * = (w + Ｖ)(w2 + Ｖ2)
      * = w*w2 + w*Ｖ2 + Ｖ*w2 - Ｖ*Ｖ2 + Ｖ×Ｖ2
      * = w*w2 + w*(x2i+y2j+z2k) + (xi+yj+zk)*w2 - (xi+yj+zk)*(x2i+y2j+z2k) +
      *
      *
      */

    val (aw, ax, ay, az) = tuple
    val (bw, bx, by, bz) = rhs.tuple

    val _x = ax * bw + aw * bx + ay * bz - az * by
    val _y = ay * bw + aw * by + az * bx - ax * bz
    val _z = az * bw + aw * bz + ax * by - ay * bx
    val _w = aw * bw - ax * bx - ay * by - az * bz

    Quaternion(_w, _x, _y, _z)
  }

  def tuple: (Number, Number, Number, Number) = (w,x,y,z)


  def asVector: Vector3 = Vector3(x,y,z)

  def asMatrix: Matrix4 = Matrix4(
    1-2*y^2,     2*x*y+2*w*z,     2*x*z-2*w*y,      0,
    2*x*y-2*w*z, 1-(2*x^2)-2*z^2, 2*y*z+2*w*x,      0,
    2*x*z+2*w*y, 2*y*z-2*w*x,     1-(2*x^2) -2*y^2, 0,
        0,            0,                 0,         1,
  )
}

object Quaternion{
  /**
    * [axis]をもとに[θ]回転する、というのを
    * 四元数では以下のように表現する
    *
    * q = cos(θ/2) + (xi+yj+zk)sin(θ/2)
    *
    * @param θ ラジアン角
    * @param axis 回転軸。正規化されている必要がある
    */
  def byRotate(θ: Radians, axis: Vector3): Quaternion ={
    require(axis.hasNormalized)

    val _w = cos(θ.value * 0.5).toFloat
    val (_x, _y, _z) = (axis * sin(θ.value * 0.5)).tuple

    Quaternion(_w, _x, _y, _z)
  }

  def identity: Quaternion = Quaternion(1,0,0,0)
}