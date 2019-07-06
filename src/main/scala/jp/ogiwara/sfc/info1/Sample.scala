package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math.{Matrix3, Number, Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics.elements._
import jp.ogiwara.sfc.info1.world._
import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.units.{RigidBodyLocalPosition, ShapeLocalPosition, Speeds}
import jp.ogiwara.sfc.info1.world.units.{Length, Position, Size}

import scala.collection.mutable
import Math._

/**
  * サンプルとして扱えるオブジェクトをまとめる
  */
object Sample {
  class Cube(aPosition: Position,
             id: Int,
             size: Length = 1f.m,
             static: Boolean = false,
             linerVelocity: Speeds = Speeds.origin,
             angularVelocity: Speeds = Speeds.origin
            ) extends Entity(
    EntityID(id),
    aPosition,
    Quaternion.identity,
    mutable.Map(RigidBody.key -> RigidBody(
      id = RigidBodyId(id),
      collidable = Collidable(
        shapes = Seq(
          Shape(
            mesh = Cube.mesh(size),
            offsetPosition = ShapeLocalPosition.origin,
            offsetRotation = Quaternion.identity
          )
        ),
        AABB = AxisAlignedBoundingBox(
          center = RigidBodyLocalPosition(0f.m, 0f.m, 0f.m),
          half = Size(size / 2, size / 2, size / 2)
        )
      ),
      state = State(
        position = aPosition,
        orientation = Quaternion.byRotate(0.rad, Vector3.up),
        linearVelocity = linerVelocity,
        angularVelocity = angularVelocity,
        motionType = if(static) Static else Active
      ),
      attribute = Attribute(
        inertia = Matrix3(
          (1f/12)*(2 * pow(size.meter,2)),0,0,
          0,(1f/12)*(2 * pow(size.meter,2)),0,
          0,0,(1f/12)*(2 * pow(size.meter,2)),
        ),
        mass = 5000f.kg,
        restitution = 0.0001,
        friction = 0
      )
    ))
  ){

    override def render(): Mesh = {

      println(position)

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
        ),
        edges = Seq(),
        facets = Seq()
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
      ),
      edges = Seq(
        Edge((0,1), (1,4)),
        Edge((0,2), (3,4)),
        Edge((1,3), (5,6)),
        Edge((2,3), (5,10)),
        Edge((0,4), (0,2)),
        Edge((1,5), (1,6)),
        Edge((2,6), (3,10)),
        Edge((3,7), (7,11)),
        Edge((4,5), (0,8)),
        Edge((4,6), (2,9)),
        Edge((5,7), (7,8)),
        Edge((6,7), (9,11)),

        Edge((0,5), (0,1)),
        Edge((0,6), (2,3)),
        Edge((1,2), (4,5)),
        Edge((3,5), (6,7)),
        Edge((4,7), (8,9)),
        Edge((3,6), (10,11)),
      ),
      facets = Seq(
        Facet((0,4,5),(4,8,12),Vector3(0,0,-1)),
        Facet((0,1,5),(0,5,12),Vector3(0,0,-1)),
        Facet((0,4,6),(4,9,13),Vector3(-1,0,0)),
        Facet((0,2,6),(1,6,13),Vector3(-1,0,0)),
        Facet((0,1,2),(0,1,14),Vector3(0,1,0)),
        Facet((1,2,3),(2,3,14),Vector3(0,1,0)),

        Facet((1,3,5),(2,5,15),Vector3(1,0,0)),
        Facet((3,5,7),(7,10,15),Vector3(1,0,0)),
        Facet((4,5,7),(8,10,16),Vector3(0,-1,0)),
        Facet((4,6,7),(9,11,16),Vector3(0,-1,0)),
        Facet((2,3,6),(3,6,17),Vector3(0,0,1)),
        Facet((3,6,7),(7,11,17),Vector3(0,0,1)),
      )
    )
  }


  object NormalWorld extends
    World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq()))

  object StopWorld extends World(
    systems = Seq(),
    state = WorldState(
      entities = Seq(
        new Cube(Position.origin,0,size = 1f.m)
      )
    )
  )


  object PrimitiveWorld extends
    World(
      systems = Seq(new NormalPhysicsSystem()),
      state = WorldState(
        entities = Seq(
          new Cube(
            aPosition = Position.origin,
            id = 0,
            size = 2f.m
          ),
          new Cube(
            Position(0f.m, 5f.m, 0f.m),1,
            size = 2f.m,
            linerVelocity = Speeds(0f.mPerS, (-9.8f).mPerS, 0f.mPerS)
          ),
        )
      )
    )
}
