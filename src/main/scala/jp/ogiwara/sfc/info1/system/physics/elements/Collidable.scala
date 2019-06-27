package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.world.units.{Position, Size}

case class Collidable(
                       // 凹状物体を、複数の凸状物体で表現するため
                       shapes: Seq[Shape],
                       // AABB
                       AABB: AxisAlignedBoundingBox)
