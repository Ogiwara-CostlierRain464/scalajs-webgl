package jp.ogiwara.sfc.info1.math

import Math._

/**
  * 四元数は回転軸(Vector)と回転角(Scalar)よりなる4成分で3次元空間の回転を表す
  *
  * q
  * = w + (x,y,z)
  * = w + xi + yj + zk
  */
case class Quaternion(w: Number, x: Number, y: Number, z: Number){

  def inverse: Quaternion = Quaternion(w, -x, -y, -z)

  def norm: Number =
    sqrt(pow(w,2) + pow(x,2) + pow(y,2) + pow(z, 2))

  def %*% (rhs: Quaternion): Quaternion ={
    /**
      * q * q2
      * = (w + Ｖ)(w2 + Ｖ2)
      *
      * ここで、
      * a・b (スカラ積)(内積)
      * a×b (ベクトル積)(外積)
      * より、
      * ab = -a・b + a×b (ハミルトン積)
      * を定義する
      *
      *
      */
  }
}
