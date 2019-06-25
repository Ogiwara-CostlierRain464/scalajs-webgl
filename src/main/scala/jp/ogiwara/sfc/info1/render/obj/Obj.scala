package jp.ogiwara.sfc.info1.render.obj

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.render.obj.scanner._

case class Obj(
                vertexes: Seq[Vector3],
                faces: Seq[Face],
                vertexTextures: Seq[(Float, Float)]
              ){
  def addVertex(vector: Vector3): Obj =
    copy(vertexes = vertexes :+ vector)

  def addFace(face: Face): Obj =
    copy(faces = faces :+ face)

  def addVertexTexture(vertexTexture: (Float, Float)): Obj =
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
      }else if(line.startsWith("vt")){
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