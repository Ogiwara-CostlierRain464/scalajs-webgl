package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import org.scalajs.dom.raw.WebGLRenderingContext
import WebGLRenderingContext._
import jp.ogiwara.sfc.info1.render.service.{BufferObjectService, ProgramService}

import scala.collection.mutable

/**
  * 一つの画面を表す
  */
@mutable
class Screen(implicit val gl: WebGLRenderingContext,
             val vShader: Shader,
             val fShader: Shader
            ){

  var camera: Camera = _
  val meshes: mutable.Seq[Mesh] = mutable.Seq()


  def render(): Unit ={
    require(camera != null)


  }

  def setup(): Unit ={
    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)
    val program = ProgramService.makeAndLink(vShader, fShader)

    val positionAttrLocation = gl.getAttribLocation(program, "position")
    val colorAttrLocation = gl.getAttribLocation(program, "color")
    val textureAttrLocation = gl.getAttribLocation(program, "textureCoord")

    //TODO: 複数のMeshに対応
    BufferObjectService.createVBO()
  }

  def flush(): Unit ={

  }
}
