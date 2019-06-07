package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._

case class Color(r: Int, g: Int, b: Int, alpha: Int = 0xff){
  require(r.withIn(0 to 256))
  require(g.withIn(0 to 256))
  require(b.withIn(0 to 256))
  require(alpha.withIn(0 to 256))


  /**
    * WenGLでは0~1の範囲で表す
    */
  def normalized: (Number, Number, Number, Number) =
    (r.toFloat /256, g.toFloat /256, b.toFloat /256, alpha.toFloat /256)

}

object Color{

  val black = Color(0, 0, 0)
  val white = Color(256, 256, 256)

  object material{
    val red = Color(0xf4, 0x43, 0x36)
    val purple = Color(0x9c, 0x27, 0xb0)
    val green = Color(0x4c, 0xaf, 0x50)
    val yellow = Color(0xff, 0xeb, 0x3b)
  }
}