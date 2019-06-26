package jp.ogiwara.sfc.info1.render.service

import jp.ogiwara.sfc.info1.math._
import org.scalajs.dom.raw.{WebGLBuffer, WebGLRenderingContext}
import WebGLRenderingContext._
import jp.ogiwara.sfc.info1.world.{Color, Vertex}


/**
  * BufferObjectに変換するService
  */
object BufferObjectService {
  def createVBO(vertexes: Seq[Vertex])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    /**
      *-1, 1, 0,
      * 1, 1, 0,
      *-1,-1, 0,
      * 1,-1, 0,
      *
      * が
      *
      * (v,v,v,v)
      *
      * になってる
      */
    import scalajs.js.typedarray.Float32Array

    val vbo = gl.createBuffer()
    val jsArr = vertexesToJsArray(vertexes)

    gl.bindBuffer(ARRAY_BUFFER, vbo)
    gl.bufferData(ARRAY_BUFFER, new Float32Array(jsArr), STATIC_DRAW)
    // Bufferは一個しか使えないので、必ずUnbind
    gl.bindBuffer(ARRAY_BUFFER, null)

    vbo
  }

  def createVBO2(colors: Seq[Color])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    import scalajs.js.typedarray.Float32Array

    val vbo = gl.createBuffer()
    val jsArr = colorsToJsArray(colors)

    gl.bindBuffer(ARRAY_BUFFER, vbo)
    gl.bufferData(ARRAY_BUFFER, new Float32Array(jsArr), STATIC_DRAW)
    // Bufferは一個しか使えないので、必ずUnbind
    gl.bindBuffer(ARRAY_BUFFER, null)

    vbo
  }


  def createIBO(indexes: Seq[Int])(implicit gl: WebGLRenderingContext): WebGLBuffer ={
    import scalajs.js.typedarray.Int16Array

    val arr = new scalajs.js.Array[Number]
    indexes.foreach(i => arr.push(i.toFloat))

    val ibo = gl.createBuffer()
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, ibo)
    gl.bufferData(ELEMENT_ARRAY_BUFFER, new Int16Array(arr), usage = STATIC_DRAW)
    gl.bindBuffer(ELEMENT_ARRAY_BUFFER, null)

    ibo
  }



  def vertexesToJsArray(vertexes: Seq[Vertex]): scalajs.js.Array[Number] ={
    import scalajs.js.Array

    val arr = new Array[Number]
    vertexes.foreach { v =>

      arr.push(v.position.x.meter, v.position.y.meter, v.position.z.meter)
    }

    arr
  }

  def colorsToJsArray(colors: Seq[Color]): scalajs.js.Array[Number] ={
    import scalajs.js.Array

    val arr = new Array[Number]
    colors.foreach { c =>

      val normalized = c.normalized

      arr.push(normalized._1, normalized._2, normalized._3, normalized._4)
    }

    arr
  }

}
