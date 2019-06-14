package jp.ogiwara.sfc.info1.world.sample

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.physics._
import jp.ogiwara.sfc.info1.render.{Color, Mesh, Position}
import jp.ogiwara.sfc.info1.world.{Entity, EntityID}
import jp.ogiwara.sfc.info1.math._

import scala.collection.mutable

class Cube(aPosition: Position, size: Number = 1, rigidBody: RigidBody) extends Entity(
  EntityID(0),
  aPosition,
  mutable.Map(RigidBody.key -> rigidBody)
){
  override def render(): Mesh = {

    Mesh(
      vertexes = Seq(
        position.vector + Vector3(0,size,0),
        position.vector + Vector3(size,size,0),
        position.vector + Vector3(0,size,size),
        position.vector + Vector3(size,size,size),
        position.vector + Vector3(0,0,0),
        position.vector + Vector3(size,0,0),
        position.vector + Vector3(0,0,size),
        position.vector + Vector3(size,0,size),
      ),
      colors = Seq(
        Color.blue,
        Color.black,
        Color.black,
        Color.black,
        Color.white,
        Color.red,
        Color.green,
        Color.black,
      ),
      indexes = Seq(
        5,4,0,
        1,5,0,
        0,4,6,
        2,0,6,
        1,0,2,
        3,1,2,
        5,1,3,
        7,5,3,
        4,5,7,
        6,4,7,
        3,2,6,
        7,3,6
      )
    )
  }
}

