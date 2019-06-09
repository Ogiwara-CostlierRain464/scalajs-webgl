package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService
import org.scalajs._
import org.scalajs.dom._
import org.scalajs.dom.ext.KeyValue
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

    var camera = Camera(
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

    var count = 0

    js.timers.setInterval(1000 / 30){

      count += 1


      screen.flush()
    }


    dom.window.addEventListener("keydown", { event: dom.KeyboardEvent =>
      val keycode = event.key

      camera = keycode match {
        case KeyValue.ArrowDown => camera.down
        case KeyValue.ArrowUp => camera.up
        case KeyValue.ArrowLeft => camera.left
        case KeyValue.ArrowRight => camera.right
        case _ => camera
      }

      println(s"$camera")

      screen.camera = camera
      screen.flush()
    })

  }

}