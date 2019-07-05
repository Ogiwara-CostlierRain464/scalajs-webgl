package jp.ogiwara.sfc.info1.system.physics

import jp.ogiwara.sfc.info1.system.physics.elements.Transform
import jp.ogiwara.sfc.info1.world.Mesh

package object collision {

  /**
    * 凸形状の衝突判定
    */
  def convexContact(meshA: Mesh, transformA: Transform,
                    meshB: Mesh, transformB: Transform): Unit ={
    // 座標系変換の回数を減らすため、面数の多い方を座標系の基準にとる

    if(meshA.facets.length < meshB.facets.length)
      convexContact(meshB, transformB, meshA, transformA)

    // B local -> A localへの変換
    val transfromAB = transformA
  }


}
