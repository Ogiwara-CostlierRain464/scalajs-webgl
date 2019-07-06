package jp.ogiwara.sfc.info1.math

import Math._

case class Matrix3(value:
                   (
                     Number, Number, Number,
                     Number, Number, Number,
                     Number, Number, Number
                     )
                  ){

  def -(rhs: Matrix3): Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2) = rhs.value

    Matrix3(
      a-a2, d - d2, g -g2,
      b-b2, e - e2, h -h2,
      c-c2, f - f2, i -i2,
    )
  }

  def ×(rhs: Matrix3): Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2) = rhs.value

    Matrix3(
      a*a2+d*b2+g*c2, a*d2+d*e2+g*f2, a*g2+d*h2+g*i2,
      b*a2+e*b2+h*c2, b*d2+e*e2+h*f2, b*g2+e*h2+h*i2,
      c*a2+f*b2+i*c2, c*d2+f*e2+i*f2, c*g2+f*h2+i*i2,
    )
  }

  def ×(rhs: Vector3): Vector3 ={
    val (a,b,c,d,e,f,g,h,i) = value
    val (j,k,l) = rhs.tuple

    Vector3(
      a*j+d*k+g*l,
      b*j+e*k+h*l,
      c*j+f*k+i*l,
    )
  }

  def *(scala: Number): Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value

    Matrix3(
      a*scala, d*scala, g*scala,
      b*scala, e*scala, h*scala,
      c*scala, f*scala, i*scala,
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

  /**
    * 逆行列
    */
  def inverse: Matrix3 ={

    val (a,b,c,d,e,f,g,h,i) = value

    val det = a*e*i+d*h*c+g*b*f-g*e*c-d*b*i-a*h*f
    if(det == 0) return this

    val right = Matrix3(
      e*i-h*f, -(d*i-g*f), d*h-g*e,
      -(b*i-h*c), a*i-g*c, -(a*h-g*b),
      b*f-e*c, -(a*f-d*c), a*e-d*b
    )

    right * (1f / det)
  }

  /**
    * 絶対値
    */
  def abs: Matrix3 ={
    val (a,b,c,d,e,f,g,h,i) = value

    Matrix3(
      a.abs, d.abs, g.abs,
      b.abs, e.abs, h.abs,
      c.abs, f.abs, i.abs,
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

  // Construct a 3x3 matrix to perform scaling
  def scale(vec: Vector3): Matrix3 = Matrix3(
    vec.x, 0,0,
    0,vec.y,0,
    0,0,vec.z
  )
}
