package jp.ogiwara.sfc.info1.obj.scanner
import jp.ogiwara.sfc.info1.obj.Obj


sealed class VertexTextureObjScanner(val line: String) extends ObjScanner{

  override def applyTo(obj: Obj): Obj = {
    val splits = line.split(" ")
    assert(splits.length == 3)

    val x = splits(1).toFloat
    val y = splits(2).toFloat

    obj.addVertexTexture(Tuple2(x,y))
  }
}
