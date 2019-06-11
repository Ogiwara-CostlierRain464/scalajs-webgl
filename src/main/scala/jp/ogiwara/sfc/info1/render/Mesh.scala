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

    /**
      * 立方体
      */
    val cube = Mesh(
      vertexes = Seq(
        Vector3(0,1,0),
        Vector3(1,1,0),
        Vector3(0,1,1),
        Vector3(1,1,1),
        Vector3(0,0,0),
        Vector3(1,0,0),
        Vector3(0,0,1),
        Vector3(1,0,1),
      ),
      colors = Seq(
        Color.blue,
        Color.black,
        Color.black,
        Color.black,
        Color.white,
        Color.red,
        Color.green,
        Color.black,
      ),
      indexes = Seq(
        5,4,0,
        1,5,0,
        0,4,6,
        2,0,6,
        1,0,2,
        3,1,2,
        5,1,3,
        7,5,3,
        4,5,7,
        6,4,7,
        3,2,6,
        7,3,6
      )
    )
  }


}