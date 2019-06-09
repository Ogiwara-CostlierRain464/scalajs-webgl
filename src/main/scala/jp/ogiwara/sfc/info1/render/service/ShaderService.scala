package jp.ogiwara.sfc.info1.render.service

import jp.ogiwara.sfc.info1.render.{FragmentShader, Shader, ShaderType, VertexShader}
import org.scalajs.dom.raw.{Document, HTMLScriptElement, WebGLRenderingContext}
import org.scalajs.dom.raw.WebGLRenderingContext._


object ShaderService {
  def create(code: String, aType: ShaderType)(implicit gl: WebGLRenderingContext): Shader = {
    val _type = if (aType == VertexShader) VERTEX_SHADER else FRAGMENT_SHADER

    val raw = gl.createShader(_type)

    gl.shaderSource(raw, code)
    gl.compileShader(raw)

    Shader(raw, aType)
  }

  def byId(id: String, document: Document)(implicit gl: WebGLRenderingContext): Shader ={
    val elem = document.getElementById(id).asInstanceOf[HTMLScriptElement]
    val _type = if(elem.`type` == "x-shader/x-vertex") VertexShader() else FragmentShader()

    create(elem.text, _type)
  }
}

