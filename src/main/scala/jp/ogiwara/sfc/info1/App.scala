package jp.ogiwara.sfc.info1

import org.scalajs.dom.html.{Canvas, Image}
import org.scalajs._
import dom._
import org.scalajs.dom.raw._
import WebGLRenderingContext._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService

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

    val vertexes: Seq[Vertex] = Seq(
      Vector3(-1,1,0),
      Vector3(1,1,0),
      Vector3(-1,-1,0),
      Vector3(-1,1,0),
    )

    val colors = Seq(
      Color(0,0,0,256),
      Color(256,256,256,256),
      Color(0,0,0,128),
      Color(256,256,256,256),
    )

    val indexes = Seq(
      0,1,2,
      3,2,1
    )

    val mesh = Mesh(vertexes, colors, indexes)

    val camera = Camera(
      position = Vector3(5,5,5),
      lookAt = Vector3.origin,
      fovy = 90,
      aspect = 1.5,
      near = 0.1,
      far = 100
    )

    screen.camera = camera
    screen.meshes = mutable.Seq(mesh)

    screen.setup()
  }


}