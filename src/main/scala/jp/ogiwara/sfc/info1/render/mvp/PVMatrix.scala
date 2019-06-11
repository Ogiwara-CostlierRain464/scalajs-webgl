package jp.ogiwara.sfc.info1.render.mvp

import jp.ogiwara.sfc.info1.math.Matrix4

case class PVMatrix(matrix: Matrix4){
  def ×(modelMatrix: ModelMatrix): PVM_Matrix ={
    val result = matrix × modelMatrix.matrix

    PVM_Matrix(result)
  }
}
