package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.{lateInit, mutable}
import org.scalajs.dom.raw.{WebGLProgram, WebGLRenderingContext, WebGLUniformLocation}
import WebGLRenderingContext._
import jp.ogiwara.sfc.info1.render.mvp.ModelMatrix
import jp.ogiwara.sfc.info1.render.service.{BufferObjectService, ProgramService}
import jp.ogiwara.sfc.info1.world.WorldSnapshot

import scala.collection.mutable
import scala.scalajs.js

/**
  * 一つの画面を表す
  */
@mutable
class Screen(val vShader: Shader, val fShader: Shader, implicit val gl: WebGLRenderingContext){

  var camera: Camera = _
  var meshes: mutable.Seq[Mesh] = mutable.Seq()

  @lateInit
  private var program: WebGLProgram = _
  @lateInit
  private var positionAttrLocation: Int = _
  @lateInit
  private var colorAttrLocation: Int = _
  @lateInit
  private var uniLocation: WebGLUniformLocation = _
  @lateInit
  private var uniTextureLocation: WebGLUniformLocation = _

  def render(snapshot: WorldSnapshot): Unit ={

  }

  def setup(): Unit ={
    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)
    // ここでは、それぞれclear時のparamを設定している
    gl.clearColor(0.33,1,1,1)
    gl.clearDepth(1)

    program = ProgramService.makeAndLink(vShader, fShader)
    positionAttrLocation = gl.getAttribLocation(program, "position")
    colorAttrLocation = gl.getAttribLocation(program, "color")

    uniLocation = gl.getUniformLocation(program, "mvpMatrix")
    uniTextureLocation = gl.getUniformLocation(program, "texture")
  }

  def flush(): Unit ={
    require(camera != null)

    gl.clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT)

    //gl.activeTexture(TEXTURE0)

    //createTexture("wood.jpg")

    //1. Cameraの平行移動以外に、Rotationを定義する。これはそんなに難しくはないはず
    //2. 複数のMeshに対応
    // @see https://learnopengl.com/Getting-started/Camera

    val vMatrix = camera.makeVMatrix
    val pMatrix = camera.makePMatrix

    val pvMatrix = pMatrix × vMatrix

    gl.uniform1i(uniTextureLocation, 0)
    //gl.bindTexture(TEXTURE_2D, texture)

    val mMatrix = Matrix4.identity

    val mvpMatrix = pvMatrix × ModelMatrix(mMatrix)

    gl.uniformMatrix4fv(uniLocation, transpose = false, mvpMatrix.matrix.toJsArray)

    meshes.foreach { mesh =>
      val positionVBO = BufferObjectService.createVBO(mesh.vertexes)
      val colorVBO = BufferObjectService.createVBO2(mesh.colors)

      gl.bindBuffer(ARRAY_BUFFER, positionVBO)
      gl.enableVertexAttribArray(positionAttrLocation)
      gl.vertexAttribPointer(positionAttrLocation, 3, FLOAT, normalized = false, stride = 0, offset = 0)

      gl.bindBuffer(ARRAY_BUFFER, colorVBO)
      gl.enableVertexAttribArray(colorAttrLocation)
      gl.vertexAttribPointer(colorAttrLocation, 4, FLOAT, normalized = false, stride = 0, offset = 0)

      val ibo = BufferObjectService.createIBO(mesh.indexes)

      gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)

      gl.drawElements(TRIANGLES, mesh.indexes.length, UNSIGNED_SHORT, offset = 0)
    }

    gl.flush()
  }
}