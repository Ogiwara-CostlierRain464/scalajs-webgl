package jp.ogiwara.sfc.info1

case class Obj(vertex: Seq[Vector]){
  def addVertex(vector: Vector): Obj =
    copy(vertex = vertex :+ vector)

}

object Obj{
  def empty(): Obj = new Obj(vertex = Seq())

  def fromText(text: String): Obj ={

    var obj = Obj.empty()
    //一行づつ読んでいく
    text.lines.foreach { str =>
      val element: ObjScanner = str.charAt(0) match {
        case '#' => new CommentObjScanner(str)
        case 'v' => new VertexObjScanner(str)
        case  _ => ???
      }

      obj = element.applyTo(obj)
    }

    obj
  }
}

trait ObjScanner{
  def applyTo(obj: Obj): Obj
}

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

sealed class CommentObjScanner(val line: String) extends ObjScanner{
  override def applyTo(obj: Obj): Obj = {
    // 何もしない
    obj
  }
}