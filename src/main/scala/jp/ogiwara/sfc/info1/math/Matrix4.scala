package jp.ogiwara.sfc.info1.math

import Math._
import jp.ogiwara.sfc.info1.math

case class Matrix4(value: Tuple16[
  Number, Number, Number, Number, Number,
  Number, Number, Number, Number, Number,
  Number, Number, Number, Number, Number,
  Number
  ]){
  /**
    * Notice that 16 elements in the matrix are stored as
    * 1D array in column-major order.
    * | a e i m |
    * | b f j n |
    * | c g k o |
    * | d h l p |
    */

  def ×(rhs: Matrix4): Matrix4 ={
    val (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2,j2,k2,l2,m2,n2,o2,p2) = rhs.value

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

    Matrix4(
      a3, e3, i3, m3,
      b3, f3, j3, n3,
      c3, g3, k3, o3,
      d3, h3, l3, p3
    )
  }

  def +(rhs: Matrix4): Matrix4 ={
    val (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p) = value
    val (a2,b2,c2,d2,e2,f2,g2,h2,i2,j2,k2,l2,m2,n2,o2,p2) = rhs.value

    Matrix4(
      a + a2, e + e2, i + i2, m + m2,
      b + b2, f + f2, j + j2, n + n2,
      c + c2, g + g2, k + k2, o + o2,
      d + d2, h + h2, l + l2, p + p2
    )
  }

  override def toString: String = {
    s"""
       |Matrix4(
       |${value._1} ${value._5} ${value._9} ${value._13}
       |${value._2} ${value._6} ${value._10} ${value._14}
       |${value._3} ${value._7} ${value._11} ${value._15}
       |${value._4} ${value._8} ${value._12} ${value._16}
       |)
      """.stripMargin
  }

  def toJsArray: scalajs.js.Array[Double] ={
    val arr = new scalajs.js.Array[Double]()
    val (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p) = value

    arr.push(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p)

    arr
  }


  /**
    * [θ]はラジアン角で！
    */
  def rotate(θ: Radians, axis: Vector3): Matrix4 ={
    /**
      * ロドゲリスの回転公式を用いる
      * @see https://ja.wikipedia.org/wiki/ロドリゲスの回転公式
      *
      */
    require(axis.hasNormalized)

    val (a,b,c) = axis.tuple
    val d = sin(θ.value)
    val e = cos(θ.value)
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

    Matrix4(
      g * s + k * t + o * u, g * v + k * w + o * x,  g * y + k * z + o * a2, value._13,
      h * s + l * t + p * u,  h * v + l * w + p * x, h * y + l * z + p * a2, value._14,
      i * s + m * t + q * u,  i * v + m * w + q * x, i * y + m * z + q * a2, value._15,
      j * s + n * t + r * u,  j * v + n * w + r * x, j * y + n * z + r * a2, value._16
    )
  }
}

object Matrix4{

  /**
    * 列オーダーのため、そのままTupleコンストラクタを使うと転置してしまう
    * 直感的に行列が生成できるように定義。
    */
  def apply(
             a: Number, e: Number, i: Number, m: Number,
             b: Number, f: Number, j: Number, n: Number,
             c: Number, g: Number, k: Number, o: Number,
             d: Number, h: Number, l: Number, p: Number,
           ) =
    new Matrix4(
      (a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p)
    )

  def identity: Matrix4 = Matrix4(
    1,0,0,0,
    0,1,0,0,
    0,0,1,0,
    0,0,0,1
  )

  /**
    * 遠近法を表す行列を作る
    * CameraServiceとかに移動するのもいいかも…
    * @param fovy 画角。ラジアン角で指定
    * @param aspect width/height
    * @param near Front of ViewPort
    * @param far Back of ViewPort
    */
  def makePerspective(fovy: Radians, aspect: Number, near: Number, far: Number): Matrix4 ={
    /**
      * NOTE: the eye coordinates are defined in the right-handed coordinate system,
      * but NDC uses the left-handed coordinate system. That is, the camera at the origin is looking along -Z axis in eye space,
      * but it is looking along +Z axis in NDC.
      *
      * Xe,Ye,ZeをViewPortに対応付け、それをNDCの1*1*1の空間に収まるようにする
      *
      * @see http://www.songho.ca/opengl/gl_projectionmatrix.html
      *
      * だるかったから上みて
      *
      * right, left, top, bottom, near, farで指定する
      * RightとLeft, TopとBottomが対照な場合は以下のように簡素化できる
      *
      * | n/r  0       0            0      |
      * |  0  n/t      0            0      |
      * |  0   0 (-f-n)/(f-n) (-2fn)/(f-n) |
      * |  0   0      -1            0      |
      *
      * ここでは、[fovy]と[aspect]を用いている
      *
      * top = near * tan(fovy / 2)
      * right = top * aspect
      * のように対応するので、
      *
      * | fovy/aspect 0        0             0      |
      * |    0       fovy      0             0      |
      * |    0        0   (-f-n)/(f-n) (-2fn)/(f-n) |
      * |    0        0       -1             0      |
      */

    Matrix4(
      fovy.value / aspect, 0,0, 0,
      0, fovy.value, 0,0,
      0,0,  (-far-near) / (far- near) , (-2*far * near) / (far - near),
      0,0,-1,0
    )
  }
}