package jp.ogiwara.sfc.info1.render

import org.scalajs.dom.raw.WebGLShader

case class Shader(rawShader: WebGLShader, aType: ShaderType){

}

sealed trait ShaderType
case object Vertex extends ShaderType
case object Fragment extends ShaderType

