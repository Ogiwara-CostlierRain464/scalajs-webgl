package jp.ogiwara.sfc.info1

case class Vector(value: (Float, Float, Float)){
}

object Vector{
  def apply(a: Float, b: Float, c: Float) = new Vector(a,b,c)
}