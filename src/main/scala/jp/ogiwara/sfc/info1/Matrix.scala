package jp.ogiwara.sfc.info1

@deprecated
case class Matrix(value: Tuple16[
  Float, Float, Float, Float, Float,
  Float, Float, Float, Float, Float,
  Float, Float, Float, Float, Float,
  Float
  ]){

  /**
    * 外積
    */
  def %*%(other: Matrix): Matrix ={
    val (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2,j2,k2,l2,m2,n2,o2,p2) = other.value

    val a3 = a2 * a + b2 * e + c2 * i + d2 * m
    val b3 = a2 * b + b2 * f + c2 * j + d2 * n
    val c3 = a2 * c + b2 * g + c2 * k + d2 * o
    val d3 = a2 * d + b2 * h + c2 * l + d2 * p
    val e3 = e2 * a + f2 * e + g2 * i + h2 * m
    val f3 = e2 * b + f2 * f + g2 * j + h2 * n
    val g3 = e2 * c + f2 * g + g2 * k + h2 * o
    val h3 = e2 * d + f2 * h + g2 * l + h2 * p
    val i3 = i2 * a + j2 * e + k2 * i + l2 * m
    val j3 = i2 * b + j2 * f + k2 * j + l2 * n
    val k3 = i2 * c + j2 * g + k2 * k + l2 * o
    val l3 = i2 * d + j2 * h + k2 * l + l2 * p
    val m3 = m2 * a + n2 * e + o2 * i + p2 * m
    val n3 = m2 * b + n2 * f + o2 * j + p2 * n
    val o3 = m2 * c + n2 * g + o2 * k + p2 * o
    val p3 = m2 * d + n2 * h + o2 * l + p2 * p

    Matrix(a3,b3,c3,d3,e3,f3,g3,h3,i3,j3,k3,l3,m3,n3,o3,p3)
  }

  /**
    * 足し算
    */
  def +(other: Matrix): Matrix ={
    val (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2,j2,k2,l2,m2,n2,o2,p2) = other.value

    Matrix(a + a2,b + b2,c + c2,d + d2,e + e2,f + f2,g + g2,h + h2,i + i2,j + j2,k + k2,l + l2,m + m2,n + n2,o + o2,p + p2)
  }

  /**
    *
    * @param radian ラジアン角
    * @param axis 回転する軸
    */
  def rotate(radian: Float, axis: Vector): Matrix ={
    import Math._

    val (a,b,c) = axis.normalize.value
    val d = sin(radian).toFloat
    val e = cos(radian).toFloat
    val f = 1 - e
    val (
      g,h,i,j,
      k,l,m,n,
      o,p,q,r,
      _,_,_,_
      ) = value

    val s = a * a * f + e
    val t = b * a * f + c * d
    val u = c * a * f - b * d
    val v = a * b * f - c * d
    val w = b * b * f + e
    val x = c * b * f + a * d
    val y = a * c * f + b * d
    val z = b * c * f - a * d
    val a2 = c * c * f + e

    Matrix(
      g * s + k * t + o * u, h * s + l * t + p * u, i * s + m * t + q * u, j * s + n * t + r * u,
      g * v + k * w + o * x, h * v + l * w + p * x, i * v + m * w + q * x, j * v + n * w + r * x,
      g * y + k * z + o * a2, h * y + l * z + p * a2, i * y + m * z + q * a2, j * y + n * z + r * a2,
      value._13, value._14, value._15, value._16
    )
  }

  override def toString: String = {
      s"""
        |Matrix(
        |${value._1} ${value._2} ${value._3} ${value._4}
        |${value._5} ${value._6} ${value._7} ${value._8}
        |${value._9} ${value._10} ${value._11} ${value._12}
        |${value._13} ${value._14} ${value._15} ${value._16}
        |)
      """.stripMargin
  }

  def apply(index: Int) :Any = value.productElement(index)

  // JsArrayに変換
  def convert: scalajs.js.Array[Double] = {
    val result = scalajs.js.Array[Double]()
    result.push(
      value._1, value._2, value._3, value._4,
      value._5, value._6, value._7, value._8,
      value._9, value._10, value._11, value._12,
      value._13, value._14, value._15, value._16,
    )

    result
  }
}

object Matrix{
  def apply(value: Tuple16[
    Float, Float, Float, Float, Float,
    Float, Float, Float, Float, Float,
    Float, Float, Float, Float, Float,
    Float
    ]) = new Matrix(
    (
      value._1, value._2, value._3 , value._4,
      value._5, value._6, value._7 , value._8,
      value._9, value._10, value._11 , value._12,
      value._13, value._14, value._15 , value._16,
    )
  )

  def identity: Matrix = Matrix(
    1,0,0,0,
    0,1,0,0,
    0,0,1,0,
    0,0,0,1
  )

  /**
    * 透視投影
    */
  def perspective(fovy: Float, aspect: Float, near: Float, far: Float): Matrix ={
    import Math._

    val r: Float = 1 / tan(fovy * PI / 360).toFloat
    val d: Float = far - near

    Matrix(
      r / aspect, 0,0,0,
      0, r,0,0,
      0,0, -(far + near) / d,-1,
      0,0, -(far * near * 2) /d, 0
    )
  }
}
