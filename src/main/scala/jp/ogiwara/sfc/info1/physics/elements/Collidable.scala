package jp.ogiwara.sfc.info1.physics.elements

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.render.Position

case class Collidable(
                       shapes: Seq[Shape],
                       // AABBの中心
                       AABBCenter: Position,
                       // AABBのサイズの半分
                       AABBHalf: Vector3)
