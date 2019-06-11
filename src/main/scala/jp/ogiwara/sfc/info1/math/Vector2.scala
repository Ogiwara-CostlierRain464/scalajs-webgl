package jp.ogiwara.sfc.info1.math

import Math._

case class Vector2(x: Number,y: Number){

  def -(other: Vector2): Vector2 = Vector2(
    x - other.x,
    y - other.y
  )

  def +(other: Vector2): Vector2 =  Vector2(
    x + other.x,
    y + other.y
  )

  // スカラ積
  def `･`(scala: Number): Vector2 = Vector2(
    x * scala,
    y * scala
  )

  def tuple: (Number, Number) = (x,y)

  def rotate(radians: Radians): Vector2 ={
    val matrix = makeRotate(radians)

    matrix × this
  }

  def makeRotate(radians: Radians): Matrix2 ={
    val θ = radians.value

    Matrix2(
      cos(θ), -sin(θ),
      sin(θ),  cos(θ)
    )
  }
}
