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
                edges: Seq[Edge],
                facets: Seq[Facet],
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
        Color.blue, Color.blue,
        Color.green, Color.green
      ),
      indexes = Seq(
        0,1,2,3,4,5
      ),
      edges = Seq(),
      facets = Seq(),
      aType = Line
    )
  }




}