package jp.ogiwara.sfc.info1.world.sample

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.physics._
import jp.ogiwara.sfc.info1.render.{Color, Mesh, Position}
import jp.ogiwara.sfc.info1.world.{Entity, EntityID}
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.physics.elements.{Attribute, Collidable, Shape, State}

import scala.collection.mutable

class Cube(aPosition: Position, size: Number = 1) extends Entity(
  EntityID(0),
  aPosition,
  Quaternion.identity,
  mutable.Map(RigidBody.key -> RigidBody(
    collidable = Collidable(
      shapes = Seq(
        Shape(
          mesh = Cube.mesh(size),
          offsetPosition = Vector3.origin,
          offsetRotation = Quaternion.identity
        )
      ),
      AABBCenter = aPosition,
      AABBHalf = Vector3(size, size, size) / 2
    ),
    state = State(
      position = aPosition,
      orientation = Quaternion.byRotate(0.rad, Vector3.up),
      linearVelocity = Vector3.origin,
      angularVelocity = Vector3.origin
    ),
    attribute = Attribute(
      inertia = Matrix3(
        (1f/12)*(2*(size^2)),0,0,
        0,(1f/12)*(2*(size^2)),0,
        0,0,(1f/12)*(2*(size^2)),
      ),
      mass = 1f.kg,
      restitution = 0,
      friction = 0
    )
  ))
){

  override def render(): Mesh = {

    Mesh(
      vertexes = Seq(
        position.vector + Vector3(0,size,0).rotate(rotation),
        position.vector + Vector3(size,size,0).rotate(rotation),
        position.vector + Vector3(0,size,size).rotate(rotation),
        position.vector + Vector3(size,size,size).rotate(rotation),
        position.vector + Vector3(0,0,0).rotate(rotation),
        position.vector + Vector3(size,0,0).rotate(rotation),
        position.vector + Vector3(0,0,size).rotate(rotation),
        position.vector + Vector3(size,0,size).rotate(rotation),
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

object Cube{
  def mesh(size: Number): Mesh = Mesh(
    vertexes = Seq(
      Vector3(0,size,0),
      Vector3(size,size,0),
      Vector3(0,size,size),
      Vector3(size,size,size),
      Vector3(0,0,0),
      Vector3(size,0,0),
      Vector3(0,0,size),
      Vector3(size,0,size),
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