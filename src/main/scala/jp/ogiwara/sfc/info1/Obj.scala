package jp.ogiwara.sfc.info1

case class Obj(vertex: Seq[Vector]){

}

object Obj{
  def fromText(text: String): Obj ={
    //一行づつ読んでいく
    Obj(Seq())
  }
}