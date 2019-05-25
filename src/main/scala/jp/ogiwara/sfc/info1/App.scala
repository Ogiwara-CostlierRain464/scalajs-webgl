package jp.ogiwara.sfc.info1

import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas
import org.scalajs._
import dom._
import org.scalajs.dom.raw._
import WebGLRenderingContext._

import scala.scalajs.js.annotation.JSExportTopLevel

object App {
  def main(args: Array[String]): Unit = {}

  @JSExportTopLevel("start")
  def start(): Unit ={
    val canvas = document.createElement("canvas").asInstanceOf[Canvas]
    implicit val gl: WebGLRenderingContext = canvas.getContext("webgl").asInstanceOf[WebGLRenderingContext]

    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)

    val vShader = createShader("vs")
    val fShader = createShader("fs")
    val program = createProgram(vShader, fShader)

    val positionAttr = gl.getAttribLocation(program, "position")
    val colorAttr = gl.getAttribLocation(program, "color")

    // vec3 * 4
    val vertextPosition = Seq(
      0,1,0,
      1,0,0,
      -1,0,0,
      0,-1,0
    )

    // vec4 * 4
    val vertextColor = Seq(
      1,0,0,1,
      0, 1, 0, 1,
      0, 0, 1, 1,
      1, 1, 1, 1
    )

    val index = Seq(
      0,1,2,
      1,2,3
    )


  }

  def createShader(id: String)(implicit gl: WebGLRenderingContext): WebGLShader ={
    val scriptElement = document.getElementById(id).asInstanceOf[HTMLScriptElement]

    val shader =
      if(scriptElement.`type` == "x-shader/x-vertex")
        gl.createShader(VERTEX_SHADER)
      else
        gl.createShader(FRAGMENT_SHADER)

    gl.shaderSource(shader, scriptElement.text)
    gl.compileShader(shader)

    shader
  }

  def createProgram(vs: WebGLShader, fs: WebGLShader)
                   (implicit gl: WebGLRenderingContext): WebGLProgram ={
    val program = gl.createProgram()
    gl.attachShader(program, vs)
    gl.attachShader(program, fs)
    gl.linkProgram(program)
    gl.useProgram(program)

    program
  }

  def createVBO(seq: Seq[Float])(implicit gl: WebGLRenderingContext): Unit ={
    val vbo = gl.createBuffer()
    gl.bindBuffer(ARRAY_BUFFER, vbo)
    gl.bufferData(ARRAY_BUFFER, seq, STATIC_DRAW)
//todo

  }
}