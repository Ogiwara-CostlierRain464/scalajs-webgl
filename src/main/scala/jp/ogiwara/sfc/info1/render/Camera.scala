package jp.ogiwara.sfc.info1.render

case class Camera(
                   position: Position,
                   lookAt: Position,
                   fovy: Number,
                   aspect: Number,
                   near: Number,
                   far: Number
                 ){
  
}
