package jp.ogiwara.sfc.info1.obj

case class Face(elements: Seq[FaceElement]){

}

class FaceElement(vertexIndex: Int,
                  textureIndex: Option[Int],
                  normalIndex: Option[Int]){

}

object FaceElement{
  def fromStr(str: String): FaceElement ={
    // "//"がある場合は分ける
    if(str.contains("//")){
      val splits = str.split("//")
      assert(splits.length == 2)

      val vertexIndex = splits(0).toInt
      val normalIndex = splits(1).toInt

      return new FaceElement(vertexIndex, None, Option(normalIndex))
    }

    val splits = str.split("/")
    val vertexIndex = splits(0).toInt
    var textureIndex: Option[Int] = None
    var normalIndex: Option[Int] = None

    if(splits.length >= 2)
      textureIndex = Option(splits(1).toInt)

    if(splits.length >= 3)
      normalIndex = Option(splits(2).toInt)

    new FaceElement(vertexIndex, textureIndex, normalIndex)
  }
}
