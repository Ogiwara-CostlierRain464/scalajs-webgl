package jp.ogiwara.sfc.info1.math

case class Matrix3(value:
                   (
                     Number, Number, Number,
                     Number, Number, Number,
                     Number, Number, Number
                     )
                  ){

  def ×(rhs: Matrix3): Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2) = rhs.value

    Matrix3(
      a*a2+d*b2+g*c2, a*d2+d*e2+g*f2, a*g2+d*h2+g*i2,
      b*a2+e*b2+h*c2, b*d2+e*e2+h*f2, b*g2+e*h2+h*i2,
      c*a2+f*b2+i*c2, c*d2+f*e2+i*f2, c*g2+f*h2+i*i2,
    )
  }

  /**
    * 転置
    */
  def transpose: Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value

    Matrix3(
      a,b,c,
      d,e,f,
      g,h,i
    )
  }
}

object Matrix3{
  def apply(
             a: Number, d: Number, g: Number,
             b: Number, e: Number, h: Number,
             c: Number, f: Number, i: Number
           ): Matrix3 = {
    new Matrix3((a,b,c,d,e,f,g,h,i))
  }
}
