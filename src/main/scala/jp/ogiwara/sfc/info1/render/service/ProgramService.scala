package jp.ogiwara.sfc.info1.render.service

import jp.ogiwara.sfc.info1.render.Shader
import org.scalajs.dom.raw.{WebGLProgram, WebGLRenderingContext}

object ProgramService {

  /**
    * Programを作成し、GLにリンクする
    */
  def makeAndLink(vShader: Shader, fShader: Shader)(implicit gl: WebGLRenderingContext): WebGLProgram ={
    val program = gl.createProgram()
    gl.attachShader(program, vShader.rawShader)
    gl.attachShader(program, fShader.rawShader)
    gl.linkProgram(program)
    gl.useProgram(program)

    program
  }
}
