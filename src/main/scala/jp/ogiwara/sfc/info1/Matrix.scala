package jp.ogiwara.sfc.info1

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


  def apply(index: Int): Float = value(index)
}

object Matrix{
  def apply() = new Matrix(
    (
      1,0,0,0,
      0,1,0,0,
      0,0,1,0,
      0,0,0,1
    )
  )

}
