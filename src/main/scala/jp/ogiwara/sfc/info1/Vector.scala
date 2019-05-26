package jp.ogiwara.sfc.info1

import Math._

case class Vector(value: (Float, Float, Float)){

  def _1: Float = value._1
  def _2: Float = value._2
  def _3: Float = value._3


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

  /**
    * ノルム
    */
  def norm = sqrt(pow(_1,2) + pow(_2,2) + pow(_3,2))
}

object Vector{
  def apply(a: Float = 0, b: Float = 0, c: Float = 0) = new Vector(a,b,c)
}