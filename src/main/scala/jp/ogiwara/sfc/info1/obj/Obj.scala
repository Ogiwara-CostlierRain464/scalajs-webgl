package jp.ogiwara.sfc.info1.obj

import jp.ogiwara.sfc.info1.Vector
import jp.ogiwara.sfc.info1.obj.scanner.{CommentObjScanner, ObjScanner, VertexObjScanner}

case class Obj(vertex: Seq[Vector],faces: Seq[Face]){
  def addVertex(vector: Vector): Obj =
    copy(vertex = vertex :+ vector)

  def addFace(face: Face): Obj =
    copy(faces = faces :+ face)

}

object Obj{
  def empty(): Obj = new Obj(vertex = Seq(), faces = Seq())

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