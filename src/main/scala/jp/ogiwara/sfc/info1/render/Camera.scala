package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math._

case class Camera(
                   position: Position,
                   lookAt: Position,
                   fovy: Number,
                   aspect: Number,
                   near: Number,
                   far: Number
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


  //TODO カメラが見たい向きからではなく、とにかく位置移動とか簡単にしよう
}
