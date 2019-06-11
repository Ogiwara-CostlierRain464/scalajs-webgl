package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.render.mvp.{ProjectionMatrix, ViewMatrix}
import Math._

case class Camera(
                   position: Position,
                   lookAt: Position,
                   fovy: Radians,
                   aspect: Number,
                   near: Number,
                   far: Number,
                   rotateX: Radians = Radians(0),
                   rotateY: Radians = Radians(0),
                   rotateZ: Radians = Radians(0),
                 ){
  def up: Camera = copy(
    position = position.vector + Vector3(0,0.1,0),
    lookAt = lookAt.vector + Vector3(0,0.1,0),
  )
  def down: Camera = copy(
    position = position.vector - Vector3(0,0.1,0),
    lookAt = lookAt.vector - Vector3(0,0.1,0),

  )
  def left: Camera = copy(
    position = position.vector + Vector3(0.1,0,0),
    lookAt = lookAt.vector + Vector3(0.1,0,0),
  )
  def right: Camera = copy(
    position = position.vector - Vector3(0.1,0,0),
    lookAt = lookAt.vector - Vector3(0.1,0,0)
  )

  def turnRight: Camera = copy(
    rotateX = rotateX + 1.rad
  )

  def turnY: Camera = copy(
    rotateY = rotateY + 1.rad
  )

  def turnZ: Camera = copy(
    rotateZ = rotateZ + 1.rad
  )

  def makeVMatrix: ViewMatrix ={
    val matrix = makeLookAt(lookAt)

    val applyX = matrix × makeRotateX(rotateX)
    val applyY = applyX × makeRotateY(rotateY)
    val applyZ = applyY × makeRotateZ(rotateZ)

    ViewMatrix(applyZ)
  }

  def makePMatrix: ProjectionMatrix ={
    val matrix = makePerspective(fovy, aspect, near, far)

    ProjectionMatrix(matrix)
  }

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

    val forward = (position - target).normalized
    val left = (up × forward).normalized
    val trueUp = forward × left

    val (f,l,u) = (forward, left, trueUp)
    val e = position

    Matrix4(
      l.x, l.y, l.z, -l.x * e.x - l.y * e.y - l.z * e.z,
      u.x, u.y, u.z, -u.x * e.x - u.y * e.y - u.z * e.z,
      f.x, f.y, f.z, -f.x * e.x - f.y * e.y - f.z * e.z,
      0,   0,   0,                   1
    )
  }


  /**
    * 遠近法を表す行列を作る
    * CameraServiceとかに移動するのもいいかも…
    * @param fovy 画角。ラジアン角で指定
    * @param aspect width/height
    * @param near Front of ViewPort
    * @param far Back of ViewPort
    */
  def makePerspective(fovy: Radians, aspect: Number, near: Number, far: Number): Matrix4 ={
    /**
      * NOTE: the eye coordinates are defined in the right-handed coordinate system,
      * but NDC uses the left-handed coordinate system. That is, the camera at the origin is looking along -Z axis in eye space,
      * but it is looking along +Z axis in NDC.
      *
      * Xe,Ye,ZeをViewPortに対応付け、それをNDCの1*1*1の空間に収まるようにする
      *
      * @see http://www.songho.ca/opengl/gl_projectionmatrix.html
      *
      * だるかったから上みて
      *
      * right, left, top, bottom, near, farで指定する
      * RightとLeft, TopとBottomが対照な場合は以下のように簡素化できる
      *
      * | n/r  0       0            0      |
      * |  0  n/t      0            0      |
      * |  0   0 (-f-n)/(f-n) (-2fn)/(f-n) |
      * |  0   0      -1            0      |
      *
      * ここでは、[fovy]と[aspect]を用いている
      *
      * top = near * tan(fovy / 2)
      * right = top * aspect
      * のように対応するので、
      *
      * | fovy/aspect 0        0             0      |
      * |    0       fovy      0             0      |
      * |    0        0   (-f-n)/(f-n) (-2fn)/(f-n) |
      * |    0        0       -1             0      |
      */

    Matrix4(
      fovy.value / aspect, 0,0, 0,
      0, fovy.value, 0,0,
      0,0,  (-far-near) / (far- near) , (-2*far * near) / (far - near),
      0,0,-1,0
    )
  }


  def makeRotateX(radians: Radians): Matrix4 ={
    val θ = radians.value

    Matrix4(
      1,   0,      0,     0,
      0, cos(θ), -sin(θ), 0,
      0, sin(θ),  cos(θ), 0,
      0,   0,      0,     1
    )
  }

  def makeRotateY(radians: Radians): Matrix4 ={
    val θ = radians.value

    Matrix4(
      cos(θ), 0, sin(θ), 0,
       0,     1,   0,    0,
     -sin(θ), 0, cos(θ), 0,
       0,     0,   0,    1
    )
  }

  def makeRotateZ(radians: Radians): Matrix4 ={
    val θ = radians.value

    Matrix4(
      cos(θ), -sin(θ), 0, 0,
      sin(θ),  cos(θ), 0, 0,
        0,      0,     1, 0,
        0,      0,     0, 1
    )
  }



  //TODO カメラが見たい向きからではなく、とにかく位置移動とか簡単にしよう
}
