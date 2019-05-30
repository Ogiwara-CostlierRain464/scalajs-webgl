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
    for(line <- text.lines){
      Console.println(line)

      //NOTE: なぜかString.charAtが動かない

      var element: ObjScanner = null
      if(line.startsWith("#"))
        element = new CommentObjScanner(line)
      if(line.startsWith("v"))
        element = new VertexObjScanner(line)

      if(element != null)
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

sealed class FObjScanner(val line: String) extends ObjScanner {

  class FElement(vertexIndex: Int,
                 textureIndex: Option[Int],
                 normalIndex: Option[Int]){
  }

  object FElement{
    def fromStr(str: String): FElement ={
      // "//"がある場合は分ける
      if(str.contains("//")){
        val splits = str.split("//")
        assert(splits.length == 2)

        val vertexIndex = splits(0).toFloat
        val normalIndex = splits(1).toFloat

        return FElement(vertexIndex, normalIndex)
      }

      val splits = str.split("/")
      val vertexIndex = splits(0).toFloat
      var textureIndex: Option[Int] = None
      var normalIndex: Option[Int] = None

      if(splits.length >= 2)
        textureIndex = Option(splits(1).toInt)

      if(splits.length >= 3)
        normalIndex = Option(splits(2).toInt)

      FElement(vertexIndex, textureIndex, normalIndex)
    }
  }

  override def applyTo(obj: Obj): Obj = {
    val splits = line.split(" ")
    assert(splits.length == 4, "Wrong f element")


  }
}

