package jp.ogiwara.sfc.info1.obj

import Math._
import jp.ogiwara.sfc.info1.Vector

// 四元数
// q = a + bi + cj + dk
case class Quaternion(a: Float, b: Float, c: Float, d: Float){

  @inline def w: Float = a
  @inline def x: Float = b
  @inline def y: Float = c
  @inline def z: Float = d

  // 共役数
  def conjugate: Quaternion = Quaternion(a,-b,-c,-d)

  def norm: Float =
    sqrt(pow(a,2) + pow(b,2) + pow(c,2) + pow(d,2)).toFloat

  def %*% (other: Quaternion): Quaternion ={
    val (aw, ax, ay, az) = tuple
    val (bw, bx, by, bz) = other.tuple

    val _x = ax * bw + aw * bx + ay * bz - az * by
    val _y = ay * bw + aw * by + az * bx - ax * bz
    val _z = az * bw + aw * bz + ax * by - ay * bx
    val _w = aw * bw - ax * bx - ay * by - az * bz

    Quaternion(_w, _x, _y, _z)
  }

  def tuple: (Float, Float, Float, Float) = (a,b,c,d)

  def toVector: Vector = Vector(x,y,z)

  // Rotateさせる四元数を生成
  // q = cos(θ/2) + (xi + yj + zk) sin(θ/2)
  // angleはRadian角
  def rotate(angle: Float, axis: Vector): Quaternion ={
    // 回転軸を正規化
    val normalize = axis.normalize

    val _w = cos(angle * 0.5).toFloat
    val (_x, _y, _z) = (normalize * sin(angle * 0.5).toFloat).value

    Quaternion(_w, _x, _y, _z)
  }
}


object Quaternion{
  def identity: Quaternion = Quaternion(1,0,0,0)
}