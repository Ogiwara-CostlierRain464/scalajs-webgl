package jp.ogiwara.sfc.info1.render

import Math._

import jp.ogiwara.sfc.info1.math.Vector3

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

object Mesh{
  object sample{

    /**
      * 正方形
      */
    val square = Mesh(
      vertexes = Seq(
        Vector3(0,1,0),
        Vector3(1,0,0),
        Vector3(-1,0,0),
        Vector3(0,-1,0),
      ),
      colors = Seq(
        Color.black,
        Color.material.purple,
        Color.white,
        Color.white
      ),
      indexes = Seq(
        0,2,1,
        1,2,3
      )
    )
  }
}