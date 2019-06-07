package jp.ogiwara.sfc.info1.render.service

import jp.ogiwara.sfc.info1.render.{Shader, ShaderType, Vertex}
import org.scalajs.dom.raw.WebGLRenderingContext._
import org.scalajs.dom.raw.{WebGLRenderingContext, WebGLShader}
import org.scalajs.dom.raw._



object ShaderService {
  def create(code: String, aType: ShaderType)(implicit gl: WebGLRenderingContext): Unit ={
    val _type = if(aType.isInstanceOf[Vertex]) VERTEX_SHADER else FRAGMENT_SHADER

    val raw = gl.createShader(_type)

    gl.shaderSource(raw, code)
    gl.compileShader(raw)

    Shader(raw, aType)
  }
}

