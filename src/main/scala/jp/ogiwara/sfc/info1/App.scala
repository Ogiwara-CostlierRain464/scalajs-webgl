package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService
import org.scalajs._
import org.scalajs.dom._
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw._

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object App {
  def main(args: Array[String]): Unit = {}

  @JSExportTopLevel("start")
  def start(): Unit ={
    val canvas = document.getElementById("gl_canvas").asInstanceOf[Canvas]
    implicit val gl: WebGLRenderingContext = canvas.getContext("webgl").asInstanceOf[WebGLRenderingContext]

    val scriptElement = document.getElementById("vs").asInstanceOf[HTMLScriptElement]
    val vs = ShaderService.create(scriptElement.text, VertexShader)

    val scriptElement2 = document.getElementById("fs").asInstanceOf[HTMLScriptElement]
    val fs = ShaderService.create(scriptElement2.text, FragmentShader)

    val screen = Screen(vs, fs)

    val mesh = Mesh.sample.square

    val camera = Camera(
      position = Vector3(5,5,5),
      lookAt = Vector3.origin,
      fovy = 90,
      aspect = canvas.width / canvas.height,
      near = 0.1,
      far = 1000
    )

    screen.camera = camera
    screen.meshes = mutable.Seq(mesh)

    screen.setup()
    screen.flush()
  }


}