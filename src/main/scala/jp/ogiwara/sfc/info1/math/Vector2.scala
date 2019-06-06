package jp.ogiwara.sfc.info1.math


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
}
