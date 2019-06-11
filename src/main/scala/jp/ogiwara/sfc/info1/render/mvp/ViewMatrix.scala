package jp.ogiwara.sfc.info1.render.mvp

import jp.ogiwara.sfc.info1.math.Matrix4

/**
  * View transform is to convert from world space to eye space.
  */
case class ViewMatrix(matrix: Matrix4)
