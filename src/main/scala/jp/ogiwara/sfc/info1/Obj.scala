package jp.ogiwara.sfc.info1

case class Obj(vertex: Seq[Vector]){

}

object Obj{
  def empty(): Obj = new Obj(vertex = Seq())

  def fromText(text: String): Obj ={

    var obj = Obj.empty()
    //一行づつ読んでいく
    text.lines.foreach { str =>
      val element: ObjElement = str.charAt(0) match {
        case '#' => new CommentObjElement()
        case 'v' => new VertexObjElement()
        case  _ => ???
      }

      obj = element.applyTo(obj)
    }

    obj
  }
}

trait ObjElement{
  def applyTo(obj: Obj): Obj
}

sealed class VertexObjElement extends ObjElement{
  override def applyTo(obj: Obj): Obj = {
    obj
  }
}

sealed class CommentObjElement extends ObjElement{
  override def applyTo(obj: Obj): Obj = {
    // 何もしない
    obj
  }
}