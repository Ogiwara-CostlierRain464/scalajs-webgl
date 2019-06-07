package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._

case class Color(r: Int, g: Int, b: Int, alpha: Int){
  require(r.withIn(0 to 256))
  require(g.withIn(0 to 256))
  require(b.withIn(0 to 256))
  require(alpha.withIn(0 to 256))


  /**
    * WenGLでは0~1の範囲で表す
    */
  def normalized: (Number, Number, Number, Number) =
    (r/256, g/256, b/256, alpha/256)
}
