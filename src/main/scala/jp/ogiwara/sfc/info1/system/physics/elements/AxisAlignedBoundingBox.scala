package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.system.physics.units.{LocalPosition, RigidBodyLocalPosition}
import jp.ogiwara.sfc.info1.world.units.Size

/**
  * 軸平行境界ボックス
  */
case class AxisAlignedBoundingBox(
                                   // AABBの中心
                                   center: RigidBodyLocalPosition,
                                   // AABBのサイズの半分
                                   half: Size
                                 )
