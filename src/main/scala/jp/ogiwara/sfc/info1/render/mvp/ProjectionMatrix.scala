package jp.ogiwara.sfc.info1.render.mvp

import jp.ogiwara.sfc.info1.math.Matrix4

/**
  * This GL_PROJECTION matrix defines the viewing volume (frustum);
  * how the vertex data are projected onto the screen (perspective or orthogonal)
  */
case class ProjectionMatrix(matrix: Matrix4){
  def ×(viewMatrix: ViewMatrix): PVMatrix ={
    val result = matrix × viewMatrix.matrix

    PVMatrix(result)
  }
}