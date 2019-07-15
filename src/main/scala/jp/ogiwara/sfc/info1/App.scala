package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.Sample.PrimitiveWorld
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render._
import jp.ogiwara.sfc.info1.render.service.ShaderService
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.world.units.Position
import org.scalajs.dom._
import org.scalajs.dom.ext.KeyValue
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw._

import scala.collection.mutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object App {
  def main(args: Array[String]): Unit = {}

  @JSExportTopLevel("gotoBoxCase")
  def gotoBoxCase(): Unit = {
    window.location.href = "box_case.html"
  }

  @JSExportTopLevel("boxCase")
  def boxCase(): Unit = {
    execute(Sample.BoxCaseWorld)
  }

  @JSExportTopLevel("gotoFallCase")
  def gotoFallCase(): Unit = {
    window.location.href = "fall_case.html"
  }

  @JSExportTopLevel("fallCase")
  def fallCase(): Unit = {
    execute(Sample.FallCaseWorld)
  }

  @JSExportTopLevel("gotoCollisionCase")
  def gotoCollisionCase(): Unit = {
    window.location.href = "collision_case.html"
  }

  @JSExportTopLevel("collisionCase")
  def collisionCase(): Unit = {
    execute(Sample.CollisionCaseWorld)
  }

  @JSExportTopLevel("gotoFloorCase")
  def gotoFloorCase(): Unit = {
    window.location.href = "floor_case.html"
  }

  @JSExportTopLevel("floorCase")
  def floorCase(): Unit = {
    execute(Sample.FloorCaseWorld)
  }

  @JSExportTopLevel("gotoErrorCase")
  def gotoErrorCase(): Unit = {
    window.location.href = "error_case.html"
  }

  @JSExportTopLevel("errorCase")
  def errorCase(): Unit = {
    execute(Sample.ErrorCaseWorld)
  }

  def execute(world: World): Unit ={
    val canvas = document.getElementById("gl_canvas").asInstanceOf[Canvas]
    var (screen, camera) = createMetaViewer(canvas)
    screen.setup()

    var caches: mutable.Seq[String] = mutable.Seq()

    js.timers.setInterval(1000 / 60) {

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

  private def createMetaViewer(canvas: Canvas): (render.Screen, Camera) ={

    implicit val gl: WebGLRenderingContext = canvas.getContext("webgl").asInstanceOf[WebGLRenderingContext]

    val vs = ShaderService.create(vertexShader, VertexShader)
    val fs = ShaderService.create(fragmentShader, FragmentShader)

    val screen = new render.Screen(vs, fs, gl)

    val camera = Camera(
      position = Position(10f.m,10f.m,10f.m),
      lookAt = Position.origin,
      fovy = 100.rad,
      aspect = canvas.width / canvas.height,
      near = 1f.m,
      far = 1000f.km,
      rotateX = 0.rad
    )

    (screen, camera)
  }

  private val vertexShader: String =
    """
      |
      |attribute vec3 position;
      |attribute vec4 color;
      |attribute vec2 textureCoord;
      |uniform   mat4 mvpMatrix;
      |varying   vec4 vColor;
      |varying   vec2 vTextureCoord;
      |
      |void main(void){
      |    vColor = color;
      |    vTextureCoord = textureCoord;
      |    gl_Position = mvpMatrix * vec4(position, 1.0);
      |}
      |
    """.stripMargin

  private val fragmentShader: String =
    """
      |
      |precision mediump float;
      |
      |uniform sampler2D texture;
      |varying vec4 vColor;
      |varying vec2 vTextureCoord;
      |
      |void main(void){
      |    //vec4 smpColor = texture2D(texture, vTextureCoord);
      |    //gl_FragColor = vColor * smpColor;
      |    gl_FragColor = vColor;
      |}
      |
    """.stripMargin
}