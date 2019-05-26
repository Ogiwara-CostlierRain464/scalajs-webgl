package jp.ogiwara.sfc.info1

import org.scalajs.dom.html
import org.scalajs.dom.html.Canvas
import org.scalajs._
import dom._
import org.scalajs.dom.raw._
import WebGLRenderingContext._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object App {
  def main(args: Array[String]): Unit = {}

  @JSExportTopLevel("start")
  def start(): Unit ={
    val canvas = document.getElementById("gl_canvas").asInstanceOf[Canvas]
    implicit val gl: WebGLRenderingContext = canvas.getContext("webgl").asInstanceOf[WebGLRenderingContext]

    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)

    val vShader = createShader("vs")
    val fShader = createShader("fs")
    val program = createProgram(vShader, fShader)

    val positionAttr = gl.getAttribLocation(program, "position")
    val colorAttr = gl.getAttribLocation(program, "color")

    // vec3 * 4
    val vertextPosition = scalajs.js.Array[Float]()
    vertextPosition.push(
      0, 1, 0,
      1, 0, 0,
      -1, 0, 0,
      0, -1, 0
    )

    // vec4 * 4
    val vertextColor = scalajs.js.Array[Float]()
    vertextColor.push(
      1,0,0,1,
      0,1,0,1,
      0,0,1,1,
      1,1,1,1
    )

    val index = scalajs.js.Array[Float]()
    index.push(
      0,1,2,
      1,2,3
    )

    val positionVBO = createVBO(vertextPosition)
    val colorVBO = createVBO(vertextColor)

    setAttribute(Seq(positionVBO, colorVBO), Seq(positionAttr, colorAttr), Seq(3,4))

    val ibo = createIBO(index)

    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)

    val uniLocation = gl.getUniformLocation(program, "mvpMatrix")


    val vMatrix = Vector(0,1,3).lookAt(Vector(0,0,0))
    val pMatrix = Matrix.perspective(90, canvas.width.toFloat / canvas.height.toFloat, 0.1.toFloat ,100)
    val tmp = pMatrix %*% vMatrix

    var count = 0

    js.timers.setInterval(1000 / 30) {
      gl.clearColor(0,0,0,1)
      gl.clearDepth(1)
      gl.clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT)

      count += 1

      val rad = ((count % 360) * Math.PI / 180).toFloat

      val mMatrix = Matrix.identity.rotate(rad, Vector.up)

      val mvpMatrix = tmp %*% mMatrix

      gl.uniformMatrix4fv(uniLocation, transpose = false, mvpMatrix.convert)
      gl.drawElements(TRIANGLES, index.length, UNSIGNED_SHORT, offset = 0)
      gl.flush()
    }
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

  def createVBO(array: scalajs.js.Array[Float])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    import scalajs.js.typedarray.Float32Array

    val vbo = gl.createBuffer()
    gl.bindBuffer(ARRAY_BUFFER, vbo)
    gl.bufferData(ARRAY_BUFFER, new Float32Array(array), STATIC_DRAW)
    gl.bindBuffer(ARRAY_BUFFER, null)

    vbo
  }

  // VBOをバインドし登録する関数
  def setAttribute(vboArr: Seq[WebGLBuffer],
                   attLocationArr: Seq[Int],
                   attSizeArr: Seq[Int])(implicit gl: WebGLRenderingContext): Unit ={
    vboArr.zipWithIndex.foreach { case (buffer, index) =>
        gl.bindBuffer(ARRAY_BUFFER, buffer)
        gl.enableVertexAttribArray(attLocationArr(index))
        gl.vertexAttribPointer(attLocationArr(index), attSizeArr(index), FLOAT, normalized = false,stride =  0,offset = 0)
    }
  }

  def createIBO(array: scalajs.js.Array[Float])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    import scalajs.js.typedarray.Int16Array

    val ibo = gl.createBuffer()
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)
    gl.bufferData(ELEMENT_ARRAY_BUFFER, new Int16Array(array),usage = STATIC_DRAW)
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, null)

    ibo
  }
}