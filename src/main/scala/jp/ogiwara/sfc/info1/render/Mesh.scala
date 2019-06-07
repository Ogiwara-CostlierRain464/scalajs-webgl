package jp.ogiwara.sfc.info1.render

import Math._

/**
  * ポリゴンが集まってできる、一つの立体のこと
  *
  * IBOも含めて考えると、ここで頂点、色、テクスチャ、インデックス
  * を指定することになる
  */
case class Mesh(vertexes: Seq[Vertex], colors: Seq[Color], indexes: Seq[Int]){
  require(vertexes.length == colors.length)
  require(vertexes.length == indexes.max + 1)


}
