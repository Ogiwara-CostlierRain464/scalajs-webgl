package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math.{Matrix3, Number, Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics.elements._
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.units.{LocalPosition, Speeds}
import jp.ogiwara.sfc.info1.world.units.{Length, Position, Size}

import scala.collection.mutable
import Math._

/**
  * サンプルとして扱えるオブジェクトをまとめる
  */
object Sample {
  class Cube(aPosition: Position, size: Length = 1f.m) extends Entity(
    EntityID(0),
    aPosition,
    Quaternion.identity,
    mutable.Map(RigidBody.key -> RigidBody(
      id = RigidBodyId(0),
      collidable = Collidable(
        shapes = Seq(
          Shape(
            mesh = Cube.mesh(size),
            offsetPosition = LocalPosition.origin,
            offsetRotation = Quaternion.identity
          )
        ),
        AABB = AxisAlignedBoundingBox(
          center = LocalPosition(0f.m, 0f.m, 0f.m),
          half = Size(size / 2, size / 2, size / 2)
        )
      ),
      state = State(
        position = aPosition,
        orientation = Quaternion.byRotate(0.rad, Vector3.up),
        linearVelocity = Speeds.origin,
        angularVelocity = Speeds.origin
      ),
      attribute = Attribute(
        inertia = Matrix3(
          (1f/12)*(2 * pow(size.meter,2)),0,0,
          0,(1f/12)*(2 * pow(size.meter,2)),0,
          0,0,(1f/12)*(2 * pow(size.meter,2)),
        ),
        mass = 1f.kg,
        restitution = 0,
        friction = 0
      )
    ))
  ){

    override def render(): Mesh = {

      // さて、回転そのものは正しいそう
      // 多分回転の中心がおかしい…

      Mesh(
        vertexes = Seq(
          Vertex((position + Position(0f.m,size,0f.m)).rotate(rotation)),
          Vertex((position + Position(size,size,0f.m)).rotate(rotation)),
          Vertex((position + Position(0f.m,size,size)).rotate(rotation)),
          Vertex((position + Position(size,size,size)).rotate(rotation)),
          Vertex((position + Position(0f.m,0f.m,0f.m)).rotate(rotation)),
          Vertex((position + Position(size,0f.m,0f.m)).rotate(rotation)),
          Vertex((position + Position(0f.m,0f.m,size)).rotate(rotation)),
          Vertex((position + Position(size,0f.m,size)).rotate(rotation)),
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
    def mesh(size: Length): Mesh = Mesh(
      vertexes = Seq(
        Vertex((0f.m,size,0f.m)),
        Vertex((size,size,0f.m)),
        Vertex((0f.m,size,size)),
        Vertex((size,size,size)),
        Vertex((0f.m,0f.m,0f.m)),
        Vertex((size,0f.m,0f.m)),
        Vertex((0f.m,0f.m,size)),
        Vertex((size,0f.m,size)),
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


  object NormalWorld extends
    World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq()))

  object StopWorld extends World(
    systems = Seq(),
    state = WorldState(
      entities = Seq(
        new Cube(Position.origin,size = 1f.m)
      )
    )
  )


  object PrimitiveWorld extends
    World(
      systems = Seq(new NormalPhysicsSystem()),
      state = WorldState(
        entities = Seq(
          new Cube(
            Position.origin,
            size = 2f.m
          ),
          new Cube(
            Position(3f.m, 0f.m, 0f.m),
            size = 2f.m
          ),

        )
      )
    )
}
