package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.mutable

import scala.collection.mutable

/**
  * 一つの画面を表す
  */
@mutable
class Screen {
  val camera: Camera = null

  val meshes: mutable.Seq[Mesh] = mutable.Seq()

  def add(mesh: Mesh): Unit ={
    meshes :+ mesh
  }

  def render(): Unit ={
    require(camera != null)
  }
}
