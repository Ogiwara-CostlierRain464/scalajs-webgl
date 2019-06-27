package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.system.physics.RigidBody
import jp.ogiwara.sfc.info1.system.physics.elements._

object BroadPhasePipeline {
  final val expand = 0.01f

  def findPair(objects: Seq[RigidBody], oldPairs: Seq[CollisionPair]): Seq[CollisionPair] ={

    var a = 0
    while(a < objects.length){
      var b = a + 1
      while(b < objects.length){
        val bodyA = objects(a)
        val bodyB = objects(b)

        if(intersectAABB(bodyA, bodyB)){

        }

        b += 1
      }
      a += 1
    }

    oldPairs
  }

  // TODO: move this method to AABB class
  def intersectAABB(bodyA: RigidBody, bodyB: RigidBody): Boolean ={
    val result = calculateAABB(bodyA, bodyB)
    val centerA = result.centerA
    val centerB = result.centerB
    val halfA = result.halfA
    val halfB = result.halfB

    if((centerA.x - centerB.x).abs > halfA.x + halfB.x) return false
    if((centerA.y - centerB.y).abs > halfA.y + halfB.y) return false
    if((centerA.z - centerB.z).abs > halfA.z + halfB.z) return false

    true
  }

  def calculateAABB(bodyA: RigidBody, bodyB: RigidBody): {
    def centerA: Vector3
    def halfA: Vector3
    def centerB: Vector3
    def halfB: Vector3
  } ={


    val stateA = bodyA.state
    val collidableA = bodyA.collidable
    val orientationA = stateA.orientation.asMatrix.asMatrix3

    val stateB = bodyB.state
    val collidableB = bodyB.collidable
    val orientationB = stateB.orientation.asMatrix.asMatrix3

    val _centerA = stateA.position.vector + (orientationA × collidableA.AABB.center.vector)
    val _halfA = orientationA.abs × (collidableA.AABB.half.vector + Vector3(expand, expand, expand)) //AABBサイズを若干拡張

    val _centerB = stateB.position.vector + (orientationB × collidableB.AABB.center.vector)
    val _halfB = orientationB.abs × (collidableB.AABB.half.vector + Vector3(expand, expand, expand)) //AABBサイズを若干拡張

    new {
      def centerA: Vector3 = _centerA
      def halfA: Vector3 = _halfA
      def centerB: Vector3 = _centerB
      def halfB: Vector3 = _halfB
    }
  }

}
