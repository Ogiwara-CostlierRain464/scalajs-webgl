package jp.ogiwara.sfc.info1.obj.scanner

import jp.ogiwara.sfc.info1.obj.{Face, FaceElement, Obj}


sealed class FaceObjScanner(val line: String) extends ObjScanner {

  override def applyTo(obj: Obj): Obj = {
    val splits = line.split(" ")
    // splitsの長さは3か4
    assert(splits.length >= 3, "Wrong face element")



    val one = FaceElement.fromStr(splits(1))
    val two = FaceElement.fromStr(splits(2))
    val three = FaceElement.fromStr(splits(3))

    var elements = Seq[FaceElement](one, two, three)

    if(splits.length >= 4){
      elements = elements :+ FaceElement.fromStr(splits(4))
    }

    obj.addFace(Face(elements))
  }
}

