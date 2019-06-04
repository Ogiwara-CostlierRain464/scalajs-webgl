package jp.ogiwara.sfc.info1.math

class Vector2(val x: Number,val y: Number){
  def -(other: Vector2): Vector2 = new Vector2(
    x - other.x,
    y - other.y
  )
}
