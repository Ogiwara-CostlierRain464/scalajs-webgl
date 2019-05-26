package jp.ogiwara.sfc.info1

import Math._

case class Vector(value: (Float, Float, Float)){

  def _1: Float = value._1
  def _2: Float = value._2
  def _3: Float = value._3

  def x: Float = _1
  def y: Float = _2
  def z: Float = _3


  def -(other: Vector): Vector = Vector(
    value._1 - other._1,
    value._2 - other._2,
    value._3 - other._3
  )

  def +(other: Vector): Vector =  Vector(
    value._1 + other._1,
    value._2 + other._2,
    value._3 + other._3
  )

  def *(scala: Float): Vector = Vector(
    value._1 * scala,
    value._2 * scala,
    value._3 * scala
  )

  /**
    * Vector同士の外積
    */
  def %*%(other: Vector): Vector = Vector(
    y * other.z - z * other.y,
    z * other.x - x * other.z,
    x * other.y - y * other.x,
  )

  /**
    * ノルム
    */
  def norm: Float = sqrt(pow(_1,2) + pow(_2,2) + pow(_3,2)).toFloat

  /**
    * 正規化
    */
  def normalize: Vector = this * (1 / this.norm)


  def lookAt(target: Vector): Matrix ={
    val z = (target - this).normalize
    val x = (Vector.up %*% z).normalize
    val y = (z %*% x).normalize

    Matrix(
      x.x, y.x, z.x,0,
      x.y, y.y, z.y,0,
      x.z, y.z, z.z,0,
      -(x.x * target.x + y.x * target.y + z.x * target.z),
      -(x.y * target.x + y.y * target.y + z.y * target.z),
      -(x.z * target.x + y.z * target.y + z.z * target.z), 1
    )
  }
}

object Vector{
  def apply(a: Float = 0, b: Float = 0, c: Float = 0) = new Vector(a,b,c)

  def up = Vector(0,1,0)
}