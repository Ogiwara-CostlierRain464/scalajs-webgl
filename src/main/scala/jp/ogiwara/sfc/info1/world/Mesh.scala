package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.world._

/**
  * ポリゴンが集まってできる、一つの立体のこと
  *
  * IBOも含めて考えると、ここで頂点、色、テクスチャ、インデックス
  * を指定することになる
  */
case class Mesh(vertexes: Seq[Vertex],
                colors: Seq[Color],
                indexes: Seq[Int],
                aType: MeshRenderType = Triangle){
  require(vertexes.length == colors.length)
  require(vertexes.length == indexes.max + 1)
}

sealed trait MeshRenderType

case object Triangle extends MeshRenderType
case object Line extends MeshRenderType
case object Point extends MeshRenderType

object Mesh{
  object sample{

    /**
      * 正方形
      */
    val square = Mesh(
      vertexes = Seq(
        Vertex((0f.m,2f.m,0f.m)),
        Vertex((1f.m,2f.m,0f.m)),
        Vertex((1f.m,1f.m,0f.m)),
        Vertex((0f.m,1f.m,0f.m)),
      ),
      colors = Seq(
        Color.black,
        Color.material.purple,
        Color.white,
        Color.white
      ),
      indexes = Seq(
        0,3,1,
        2,1,3
      )
    )

    /**
      * 立方体
      */
    val cube = Mesh(
      vertexes = Seq(
        Vertex((0f.m,1f.m,0f.m)),
        Vertex((1f.m,1f.m,0f.m)),
        Vertex((0f.m,1f.m,1f.m)),
        Vertex((1f.m,1f.m,1f.m)),
        Vertex((0f.m,0f.m,0f.m)),
        Vertex((1f.m,0f.m,0f.m)),
        Vertex((0f.m,0f.m,1f.m)),
        Vertex((1f.m,0f.m,1f.m)),
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

    /**
      * X,Y,Z軸
      */
    val axises = Mesh(
      vertexes = Seq(
        Vertex(((-100000f).m, 0f.m,0f.m)),
        Vertex((100000f.m, 0f.m,0f.m)),
        Vertex((0f.m,(-100000f).m,0f.m)),
        Vertex((0f.m,100000f.m,0f.m)),
        Vertex((0f.m,0f.m,(-100000f).m)),
        Vertex((0f.m,0f.m,100000f.m)),
      ),
      colors = Seq(
        Color.red,Color.red,
        Color.green, Color.green,
        Color.blue, Color.blue
      ),
      indexes = Seq(
        0,1,2,3,4,5
      ),
      aType = Line
    )
  }

}