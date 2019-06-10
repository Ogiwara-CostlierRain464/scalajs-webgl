package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.mutable
import org.scalajs.dom.raw.{WebGLProgram, WebGLRenderingContext}
import WebGLRenderingContext._
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
  private var program: WebGLProgram = _


  def render(snapshot: WorldSnapshot): Unit ={

  }

  def setup(): Unit ={

    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)
    // ここでは、それぞれclear時のparamを設定している
    gl.clearColor(0,0,0,1)
    gl.clearDepth(1)

    program = ProgramService.makeAndLink(vShader, fShader)

    val positionAttrLocation = gl.getAttribLocation(program, "position")
    val colorAttrLocation = gl.getAttribLocation(program, "color")

    //TODO: 複数のMeshに対応
    val mesh = meshes.head

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

  }

  def flush(): Unit ={
    require(camera != null)

    println("flushed!")

    val mesh = meshes.head

    val uniLocation = gl.getUniformLocation(program, "mvpMatrix")
    val uniTextureLocation = gl.getUniformLocation(program, "texture")

    var count = 0

    //gl.activeTexture(TEXTURE0)

    //createTexture("wood.jpg")

    js.timers.setInterval(1000 / 30) {
      gl.clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT)

      count += 1

      val rad = ((0 % 180) * Math.PI / 45).toFloat
      val rad2 = ((0 % 180) * Math.PI / 45).toFloat

      val camPosition = camera.position
      val camUpDirection = Vector3(0,1,0).rotate(rad2.toRadians, Vector3.up)

      //1. Cameraの平行移動以外に、Rotationを定義する。これはそんなに難しくはないはず
      //2. 複数のMeshに対応
      // @see https://learnopengl.com/Getting-started/Camera

      val vMatrix = camPosition.makeLookAt(camera.lookAt, up = camUpDirection)
      val pMatrix = Matrix4.makePerspective(camera.fovy.toRadians, camera.aspect, camera.near ,camera.far)
      val tmp = pMatrix × vMatrix

      gl.uniform1i(uniTextureLocation, 0)
      //gl.bindTexture(TEXTURE_2D, texture)

      val mMatrix = Matrix4.identity.rotate(rad, Vector3.up)

      val mvpMatrix = tmp × mMatrix

      gl.uniformMatrix4fv(uniLocation, transpose = false, mvpMatrix.toJsArray)
      gl.drawElements(TRIANGLES, mesh.indexes.length, UNSIGNED_SHORT, offset = 0)
      gl.flush()
    }
  }
}