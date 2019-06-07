package jp.ogiwara.sfc.info1.math

import java.lang.Math.{pow, sqrt, round}

/**
  * 方向を表すのか、それとも座標を表すのか、
  * 意味論的な違いがあることに注意する
  */
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
    * スカラー積
    */
  def *(rhs: Vector3): Number =
    x * rhs.x + y * rhs.y + z * rhs.z

  /**
    * クロス積(ベクトル積)
    */
  def ×(other: Vector3): Vector3 = Vector3(
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

  /**
    * Normalizeされているか確認
    */
  def hasNormalized: Boolean =
    round(norm) == 1


  /**
    * [this]が[target]の方向を向くための行列を作る
    */
  def makeLookAt(target: Vector3, up: Vector3 = Vector3.up): Matrix4 ={
    /**
      * カメラは原点からZの-方向を見るように固定されているので、
      * 物体がカメラの前に持ってくるよにして、擬似的にカメラを「移動」する。
      * @see http://www.songho.ca/opengl/gl_camera.html#lookat
      *
      * eye: (x_e, y_e, z_e)
      * target: (x_t, y_t, z_t)
      *
      * M_{modelView} = M_view * M_model
      * M_view = M_R * M_T
      *
      * M_R: rotating the scene with reverse orientation
      * M_T: translating the whole scene inversely from the eye position to the origin
      *
      * M_T =
      * | 1 0 0 -x_e |
      * | 0 1 0 -y_e |
      * | 0 0 1 -z_e |
      * | 0 0 0   1  |
      *
      * example:
      * | 1 0 0 -2 |   |0|   |-2|
      * | 0 1 0  0 | * |0| = | 0|
      * | 0 0 1 -3 |   |0|   |-3|
      * | 0 0 0  1 |   |1|   | 1|
      *
      * M_R =
      * | l_x u_x f_x 0 |
      * | l_y u_y f_y 0 | ** -1
      * | l_z u_z f_z 0 |
      * |  0    0  0  1 |
      *
      * 直交行列より、(正規直交基底をなすので) =
      * | l_x u_x f_x 0 | T
      * | l_y u_y f_y 0 |
      * | l_z u_z f_z 0 |
      * |  0    0  0  1 | =
      *
      * | l_x l_y l_z 0 |
      * | u_x u_y u_z 0 |
      * | f_x f_y f_z 0 |
      * |  0    0  0  1 |
      *
      * よって、
      * M_R * M_T =
      * | l_x l_y l_z 0 |     | 1 0 0 -x_e |   | l_x l_y l_z -l_x*x_e - l_y*y_e - l_z*z_e |
      * | u_x u_y u_z 0 | %*% | 0 1 0 -y_e | = | u_x u_y u_z -u_x*x_e - u_y*y_e - u_z*z_e |
      * | f_x f_y f_z 0 |     | 0 0 1 -z_e |   | f_x f_y f_z -f_x*x_e - f_y*y_e - f_z*z_e |
      * |  0    0  0  1 |     | 0 0 0   1  |   |  0   0   0                1              |
      *
      */

    val forward = (this - target).normalized
    val left = (up × forward).normalized
    val trueUp = forward × left

    val (f,l,u) = (forward, left, trueUp)
    val e = this

    Matrix4(
      l.x, l.y, l.z, -l.x * e.x - l.y * e.y - l.z * e.z,
      u.x, u.y, u.z, -u.x * e.x - u.y * e.y - u.z * e.z,
      f.x, f.y, f.z, -f.x * e.x - f.y * e.y - f.z * e.z,
       0,   0,   0,                   1
    )
  }

  /**
    * この座標を、[axis]をもとに[radian]度回転して移動する
    * @see https://mathtrain.jp/quaternion
    *
    * @param θ ラジアン角
    * @param axis 回転軸。正規化されている必要がある
    */
  def rotate(θ: Number, axis: Vector3): Vector3 ={
    /**
      *
      * aをqによって回転して、bにするなら
      * b = qa bar(q)
      *
      */
    val q = Quaternion.byRotate(θ, axis)

    rotate(by = q)
  }

  def rotate(by: Quaternion): Vector3 ={
    val q = by
    val a = this

    val b = q * a.asQuaternion * q.inverse

    b.asVector
  }

  def tuple: (Number, Number, Number) = (x,y,z)

  /**
    * Vectorを四元数と見なして計算するときに使う
    */
  def asQuaternion: Quaternion = Quaternion(0, x, y, z)
}

object Vector3{
  @inline def up = Vector3(0,1,0)
  // 原点
  @inline def origin = Vector3(0,0,0)

  implicit def tuple2Vector3(tuple: (Number, Number, Number)) =
    Vector3(tuple._1, tuple._2, tuple._3)
}
