package jp.ogiwara.sfc.info1.render

import jp.ogiwara.sfc.info1.math.Vector3

case class Position(vector: Vector3){

}

object Position{
  implicit def vector2Position(vector: Vector3): Position =
    Position(vector)

  implicit def position2Vector(position: Position): Vector3 =
    position.vector
}