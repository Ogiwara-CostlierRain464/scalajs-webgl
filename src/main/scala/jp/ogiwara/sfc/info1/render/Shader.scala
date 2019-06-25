package jp.ogiwara.sfc.info1.render

import org.scalajs.dom.raw.WebGLShader

case class Shader(rawShader: WebGLShader, aType: ShaderType)

sealed trait ShaderType
case object VertexShader extends ShaderType
case object FragmentShader extends ShaderType

