package jp.ogiwara.sfc.info1.obj.scanner

import jp.ogiwara.sfc.info1.obj.Obj
import jp.ogiwara.sfc.info1.Vector

sealed class VertexObjScanner(val line: String) extends ObjScanner{
  override def applyTo(obj: Obj): Obj = {
    val splits = line.split(" ")
    assert(splits.length == 4, "Wrong vertex element")

    val x = splits(1).toFloat
    val y = splits(2).toFloat
    val z = splits(3).toFloat

    obj.addVertex(Vector(x,y,z))
  }
}