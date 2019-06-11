package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService
import jp.ogiwara.sfc.info1.world.NormalWorld
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

    val mesh = Mesh.sample.square

    var camera = Camera(
      position = Vector3(5,5,5),
      lookAt = Vector3.origin,
      fovy = 90.rad,
      aspect = canvas.width / canvas.height,
      near = 0.1,
      far = 1000
    )

    screen.camera = camera
    screen.meshes = mutable.Seq(mesh)

    screen.setup()

    screen.flush()


    /*
    val world = NormalWorld
    js.timers.setInterval(1000 / 60){
      val view = world.update()
      screen.render(view)
    }
    */

    dom.window.addEventListener("keydown", { event: dom.KeyboardEvent =>
      val keycode = event.key

      println(keycode)

      camera = keycode match {
        case "s" => camera.down
        case "w" => camera.up
        case "a" => camera.left
        case "d" => camera.right
        case _ => camera
      }

      println(s"$camera")

      screen.camera = camera
      screen.flush()
    })

  }

}