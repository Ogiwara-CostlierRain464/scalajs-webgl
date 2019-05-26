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
    x - other.x,
    y - other.y,
    z - other.z
  )

  def +(other: Vector): Vector =  Vector(
    x + other.x,
    y + other.y,
    z + other.z
  )

  def *(scala: Float): Vector = Vector(
    x * scala,
    y * scala,
    z * scala
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
  def norm: Float = sqrt(pow(x,2) + pow(y,2) + pow(z,2)).toFloat

  /**
    * 正規化
    */
  def normalize: Vector = {
    val norm = this.norm

    if(norm == 0)
      Vector(0,0,0)
    else
      this * (1 / this.norm)
  }


  def lookAt(target: Vector): Matrix ={
    val z = (this - target).normalize
    val x = (Vector.up %*% z).normalize
    val y = (z %*% x).normalize

    Matrix(
      x.x, y.x, z.x,0,
      x.y, y.y, z.y,0,
      x.z, y.z, z.z,0,
      -(x.x * this.x + x.y * this.y + x.z * this.z),
      -(y.x * this.x + y.y * this.y + y.z * this.z),
      -(z.x * this.x + z.y * this.y + z.z * this.z), 1
    )
  }
}

object Vector{
  def apply(a: Float = 0, b: Float = 0, c: Float = 0) = new Vector(a,b,c)

  def up = Vector(0,1,0)
}