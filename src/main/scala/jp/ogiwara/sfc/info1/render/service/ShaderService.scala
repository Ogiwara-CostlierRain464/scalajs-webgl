package jp.ogiwara.sfc.info1.render.service

import jp.ogiwara.sfc.info1.render.{Shader, ShaderType, VertexShader}
import org.scalajs.dom.raw.WebGLRenderingContext
import org.scalajs.dom.raw.WebGLRenderingContext._


object ShaderService {
  def create(code: String, aType: ShaderType)(implicit gl: WebGLRenderingContext): Shader ={
    val _type = if(aType == VertexShader) VERTEX_SHADER else FRAGMENT_SHADER

    val raw = gl.createShader(_type)

    gl.shaderSource(raw, code)
    gl.compileShader(raw)

    Shader(raw, aType)
  }
}

