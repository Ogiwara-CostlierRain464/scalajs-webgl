package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.world.units.Position

case class Collidable(
                       // 凹状物体を、複数の凸状物体で表現するため
                       shapes: Seq[Shape],
                       // AABBの中心
                       AABBCenter: Position,
                       // AABBのサイズの半分
                       AABBHalf: Vector3)
