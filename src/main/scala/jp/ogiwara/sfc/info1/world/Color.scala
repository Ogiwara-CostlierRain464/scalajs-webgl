package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.math._

case class Color(r: Int, g: Int, b: Int, alpha: Int = 0xff){
  require(r.withIn(0 to 255))
  require(g.withIn(0 to 255))
  require(b.withIn(0 to 255))
  require(alpha.withIn(0 to 255))


  /**
    * WenGLでは0~1の範囲で表す
    */
  def normalized: (Number, Number, Number, Number) =
    (r.toFloat /256, g.toFloat /256, b.toFloat /256, alpha.toFloat /256)

}

object Color{

  val black = Color(0, 0, 0)
  val white = Color(255, 255, 255)
  val red = Color(255, 0, 0)
  val green = Color(0, 255, 0)
  val blue = Color(0, 0, 255)



  object material{
    val red = Color(0xf4, 0x43, 0x36)
    val purple = Color(0x9c, 0x27, 0xb0)
    val green = Color(0x4c, 0xaf, 0x50)
    val yellow = Color(0xff, 0xeb, 0x3b)
  }

  object minecraft{
    val gold = Color(255, 170, 0)
    val red = Color(255, 85, 85)
    val blue = Color(85, 85, 255)
    val yellow = Color(255, 255, 85)
  }
}