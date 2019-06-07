package jp.ogiwara.sfc.info1

import org.scalajs.dom.html.{Canvas, Image}
import org.scalajs._
import dom._
import org.scalajs.dom.raw._
import WebGLRenderingContext._
import jp.ogiwara.sfc.info1.math._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object App {
  def main(args: Array[String]): Unit = {}

  var texture: WebGLTexture = _

  @JSExportTopLevel("start")
  def start(): Unit ={
    import Math._

    val canvas = document.getElementById("gl_canvas").asInstanceOf[Canvas]
    implicit val gl: WebGLRenderingContext = canvas.getContext("webgl").asInstanceOf[WebGLRenderingContext]

    gl.enable(CULL_FACE)
    gl.enable(DEPTH_TEST)

    val vShader = createShader("vs")
    val fShader = createShader("fs")
    val program = createProgram(vShader, fShader)

    val positionAttr = gl.getAttribLocation(program, "position")
    val colorAttr = gl.getAttribLocation(program, "color")
    val textureAttr = gl.getAttribLocation(program, "textureCoord")

    // vec3 * 4
    val vertexPosition = scalajs.js.Array[Float]()
    vertexPosition.push(
      -1.0f,  1.0f,  0.0f,
      1.0f,  1.0f,  0.0f,
      -1.0f, -1.0f,  0.0f,
      1.0f, -1.0f,  0.0f
    )

    // vec4 * 4
    val vertexColor = scalajs.js.Array[Float]()
    vertexColor.push(
      1,1,1,1,
      1,1,1,1,
      1,1,1,1,
      1,1,1,1,
    )

    val textureCoord = scalajs.js.Array[Float]()
    textureCoord.push(
      0, 0,
      1, 0,
      0, 1,
      1, 1
    )

    val index = scalajs.js.Array[Int]()
    index.push(
      0, 1, 2,
      3, 2, 1
    )

    val positionVBO = createVBO(vertexPosition)
    val colorVBO = createVBO(vertexColor)
    val textureCoordVBO = createVBO(textureCoord)

    setAttribute(
      Seq(positionVBO, colorVBO, textureCoordVBO),
      Seq(positionAttr, colorAttr, textureAttr),
      // 各Vertexのサイズを指定
      Seq(3,4,2))

    val ibo = createIBO(index)

    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)

    val uniLocation = gl.getUniformLocation(program, "mvpMatrix")
    val uniTextureLocation = gl.getUniformLocation(program, "texture")

    var count = 0

    // ここでは、それぞれclear時のparamを設定している
    gl.clearColor(0,0,0,1)
    gl.clearDepth(1)

    gl.activeTexture(TEXTURE0)

    createTexture("wood.jpg")

    js.timers.setInterval(1000 / 30) {
      gl.clear(COLOR_BUFFER_BIT | DEPTH_BUFFER_BIT)

      count += 1

      val rad = ((count % 180) * Math.PI / 45).toFloat
      val rad2 = ((0 % 180) * Math.PI / 45).toFloat

      val camPosition = Vector3(-5,5,-5).rotate(rad2.toRadians, Vector3.up)
      val camUpDirection = Vector3(0,1,0).rotate(rad2.toRadians, Vector3.up)

      val vMatrix = camPosition.makeLookAt(Vector3(0,0,0), up = camUpDirection)
      val pMatrix = Matrix4.makePerspective(90.toRadians, canvas.width.toFloat / canvas.height.toFloat, 0.1 ,100)
      val tmp = pMatrix × vMatrix

      gl.uniform1i(uniTextureLocation, 0)
      gl.bindTexture(TEXTURE_2D, texture)

      val mMatrix = Matrix4.identity.rotate(rad, Vector3.up)

      val mvpMatrix = tmp × mMatrix

      gl.uniformMatrix4fv(uniLocation, transpose = false, mvpMatrix.toJsArray)
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

  def createIBO(array: scalajs.js.Array[Int])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    import scalajs.js.typedarray.Int16Array

    val ibo = gl.createBuffer()
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)
    gl.bufferData(ELEMENT_ARRAY_BUFFER, new Int16Array(array),usage = STATIC_DRAW)
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, null)

    ibo
  }

  def createTexture(path: String)(implicit gl: WebGLRenderingContext): Unit ={
    val img = document.createElement("img").asInstanceOf[Image]

    img.onload = { _ =>

      val tex = gl.createTexture()
      gl.bindTexture(TEXTURE_2D, tex)
      gl.texImage2D(TEXTURE_2D, 0, RGBA, RGBA, UNSIGNED_BYTE, img)
      gl.generateMipmap(TEXTURE_2D)
      gl.bindTexture(TEXTURE_2D, null)

      texture = tex
    }

    img.src = path
  }
}