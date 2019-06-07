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
  
}
