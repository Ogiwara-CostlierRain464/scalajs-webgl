package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.math.Vector3

// 三角形面
case class Facet(
                  // 頂点インデックス
                  vertexIds: (Int, Int, Int),
                  // エッジインデックス
                  edgeIds: (Int, Int, Int),
                  // 面法線ベクトル
                  normal: Vector3
                )
