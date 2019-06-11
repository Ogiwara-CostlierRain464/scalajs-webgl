package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService
import jp.ogiwara.sfc.info1.world.{NormalWorld, PrimitiveWorld}
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

    val vs = ShaderService.byId("vs", document)
    val fs = ShaderService.byId("fs", document)

    val screen = new render.Screen(vs, fs, gl)
    screen.setup()

    var camera = Camera(
      position = Vector3(3,3,3),
      lookAt = Vector3.origin,
      fovy = 90.rad,
      aspect = canvas.width / canvas.height,
      near = 0.1,
      far = 1000,
      rotateX = Radians(0)
    )

    val world = PrimitiveWorld

    var caches: mutable.Seq[String] = mutable.Seq()

    js.timers.setInterval(1000 / 30){

      caches.foreach { code =>
        camera = code match {
          case "w" => camera.front
          case "s" => camera.back
          case "a" => camera.left
          case "d" => camera.right
          case "j" => camera.up
          case "k" => camera.down
          case KeyValue.ArrowUp => camera.lookUp
          case KeyValue.ArrowDown => camera.lookDown
          case KeyValue.ArrowLeft => camera.lookLeft
          case KeyValue.ArrowRight => camera.lookRight
          case _ => camera
        }
      }

      caches = mutable.Seq()

      val snap = world.update()

      screen.render(snap, camera)
    }

    document.body.onkeydown = { event =>
      val keycode = event.key

      caches = caches :+ keycode
    }
  }

}