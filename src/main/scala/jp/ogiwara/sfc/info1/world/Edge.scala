package jp.ogiwara.sfc.info1.world

// 辺
case class Edge(
                 // 端点の頂点インデックス
                 vertexIds: (Int, Int),
                 // 共有する面インデックス
                 faceIds: (Int, Int),
                 // エッジの種類
                 aType: EdgeType = Convex
               )

sealed trait EdgeType
// 凸エッジ
case object Convex extends EdgeType
// 凹エッジ
case object Concave extends EdgeType
// 平坦エッジ
case object Flat extends EdgeType
