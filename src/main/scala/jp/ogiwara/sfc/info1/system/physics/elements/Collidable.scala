package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.world.units.{Position, Size}

/**
  *　形状の実態を複数格納するコンテナ
  */
case class Collidable(
                       // 凹状物体を、複数の凸状物体で表現するため
                       shapes: Seq[Shape],
                       AABB: AxisAlignedBoundingBox)
