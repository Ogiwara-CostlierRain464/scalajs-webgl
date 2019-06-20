package jp.ogiwara.sfc.info1.math

case class Matrix3(value:
                   (
                     Number, Number, Number,
                     Number, Number, Number,
                     Number, Number, Number
                     )
                  ){

}

object Matrix3{
  def apply(
             a: Number, d: Number, h: Number,
             b: Number, e: Number, i: Number,
             c: Number, f: Number, j: Number
           ): Matrix3 = {
    new Matrix3((a,b,c,d,e,f,h,i,j))
  }
}
