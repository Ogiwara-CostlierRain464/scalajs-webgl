package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import org.scalajs.dom.raw.WebGLRenderingContext
import WebGLRenderingContext._


import scala.collection.mutable

/**
  * 一つの画面を表す
  */
@mutable
class Screen(val gl: WebGLRenderingContext){

  var camera: Camera = _
  val meshes: mutable.Seq[Mesh] = mutable.Seq()


  def render(): Unit ={
    require(camera != null)


  }

  def setup(): Unit ={
    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)
  }

  def flush(): Unit ={

  }
}
