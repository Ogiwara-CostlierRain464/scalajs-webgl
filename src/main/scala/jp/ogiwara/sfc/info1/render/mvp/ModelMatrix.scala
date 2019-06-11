package jp.ogiwara.sfc.info1.render.mvp

import jp.ogiwara.sfc.info1.math.Matrix4

/**
  *  Model transform is to convert from object space to world space.
  *  ローカル座標からワールド座標に変換する行列
  */
case class ModelMatrix(matrix: Matrix4)
