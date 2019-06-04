package jp.ogiwara.sfc.info1.math

import java.lang.Math.{pow, sqrt}


case class Vector3(x: Number, y: Number,z: Number){
  /**
    * OpenGL系は右手系座標
    * x,zが水平、yが垂直方向
    */

  def -(other: Vector3): Vector3 = Vector3(
    x - other.x,
    y - other.y,
    z - other.z
  )

  def +(other: Vector3): Vector3 =  Vector3(
    x + other.x,
    y + other.y,
    z + other.z
  )

  def *(scala: Number): Vector3 = Vector3(
    x * scala,
    y * scala,
    z * scala
  )

  /**
    * Vector同士の外積
    */
  def %*%(other: Vector3): Vector3 = Vector3(
    y * other.z - z * other.y,
    z * other.x - x * other.z,
    x * other.y - y * other.x,
  )

  /**
    * ノルム
    *
    */
  def norm: Number =
    // sqrt(x^2 + y^2 + z^2)
    sqrt(pow(x,2) + pow(y,2) + pow(z,2))


  /**
    * 正規化
    */
  def normalized: Vector3 = {
    val norm = this.norm

    if(norm == 0)
      Vector3(0,0,0)
    else
      this * (1 / this.norm)
  }
}

object Vector3{
  @inline def up = Vector3(0,1,0)
}
