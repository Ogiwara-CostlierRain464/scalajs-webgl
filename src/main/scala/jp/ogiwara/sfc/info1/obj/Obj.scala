package jp.ogiwara.sfc.info1.obj

import jp.ogiwara.sfc.info1.Vector
import jp.ogiwara.sfc.info1.obj.scanner._

case class Obj(
                vertexes: Seq[Vector],
                faces: Seq[Face],
                vertexTextures: Seq[Tuple2[Float, Float]]
              ){
  def addVertex(vector: Vector): Obj =
    copy(vertexes = vertexes :+ vector)

  def addFace(face: Face): Obj =
    copy(faces = faces :+ face)

  def addVertexTexture(vertexTexture: Tuple2[Float, Float]): Obj =
    copy(vertexTextures = vertexTextures :+ vertexTexture)

  override def toString: String = s"Obj(vertex=$vertexes faces=$faces)"

}

object Obj{
  def empty(): Obj = new Obj(
    vertexes = Seq(),
    faces = Seq(),
    vertexTextures = Seq()
  )

  def fromText(text: String): Obj ={

    var obj = Obj.empty()

    //一行づつ読んでいく
    for(line <- text.lines){
      Console.println(line)

      //NOTE: なぜかString.charAtが動かない

      var element: ObjScanner = null
      if(line.startsWith("#")){
        element = new CommentObjScanner(line)
      }else if(line.startsWith("vs")){
        element = new VertexTextureObjScanner(line)
      }else if(line.startsWith("v")){
        element = new VertexObjScanner(line)
      }else if(line.startsWith("f")){
        element = new FaceObjScanner(line)
      }

      if(element != null)
        obj = element.applyTo(obj)
    }

    obj
  }
}