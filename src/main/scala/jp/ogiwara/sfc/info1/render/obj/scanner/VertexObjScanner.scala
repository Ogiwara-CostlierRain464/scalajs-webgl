package jp.ogiwara.sfc.info1.render.obj.scanner

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.render.obj.Obj

sealed class VertexObjScanner(val line: String) extends ObjScanner{
  override def applyTo(obj: Obj): Obj = {
    val splits = line.split(" ")
    assert(splits.length == 4, "Wrong vertex element")

    val x = splits(1).toFloat
    val y = splits(2).toFloat
    val z = splits(3).toFloat

    obj.addVertex(Vector3(x,y,z))
  }
}