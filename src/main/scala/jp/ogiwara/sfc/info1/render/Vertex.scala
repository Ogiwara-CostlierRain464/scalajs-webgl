package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math.Vector3

/**
  * 一つの頂点を表す
  */
case class Vertex(position: Vector3){

}

object Vertex{
  implicit def vector2Vertex(vector: Vector3): Vertex = Vertex(vector)
}