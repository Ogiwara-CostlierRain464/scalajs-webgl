package jp.ogiwara.sfc.info1.math

case class Matrix2(value: (Number, Number, Number, Number)){

  def ×(rhs: Vector2): Vector2 ={
    val (a,b,c,d) = value
    val (e,f) = rhs.tuple

    Vector2(
      a*e + c*f,
      b*e + d*f
    )
  }
}

object Matrix2{
  def apply(
           a: Number, c: Number, b: Number, d: Number
           ): Unit ={
    new Matrix2((a,b,c,d))
  }
}