package jp.ogiwara.sfc.info1.physics.pipeline

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.physics.RigidBody
import jp.ogiwara.sfc.info1.physics.elements._

import scala.collection.mutable

object BroadPhasePipeline {
  final val expand = 0.01f

  def broadPhase(objects: Seq[RigidBody], oldPairs: Seq[Pair]): (Seq[RigidBody], Seq[Pair]) ={

    // 総当たりで、AABB(Axis-Aligned Bounding Box)交差ペアを見つける
    // NOTE: そのうちSweet and prune手法と木構造を使うよ
    val newPairs: mutable.Seq[Pair] = mutable.Seq()

    var i = 0
    while(i < objects.length){
      var j = i + 1
      while(j < objects.length){

        //  body comes here
        val stateA = objects(i).state
        val collidableA = objects(i).collidable
        val stateB = objects(j).state
        val collidableB = objects(j).collidable

        val orientationA = stateA.orientation.asMatrix.asMatrix3
        val centerA = stateA.position.vector + (orientationA × collidableA.AABBCenter.vector)
        val halfA = orientationA.abs × (collidableA.AABBHalf + Vector3(expand, expand, expand)) //AABBサイズを若干拡張

        val orientationB = stateB.orientation.asMatrix.asMatrix3
        val centerB = stateB.position.vector + (orientationB × collidableB.AABBCenter.vector)
        val halfB = orientationB.abs × (collidableB.AABBHalf + Vector3(expand, expand, expand)) //AABBサイズを若干拡張

        if(intersectAABB(centerA,halfA, centerB, halfB)){
          newPairs :+ Pair(
            null,
            null,
            if(i<j)i else j,
            if(i<j)j else i
          )
        }

        j += 1
      }
      i += 1
    }

    // ここから先は、自分で実装して行けばわかるだろう…

    val outNewPairs: mutable.Seq[Pair] = mutable.Seq()
    val outKeepPairs: mutable.Seq[Pair] = mutable.Seq()

    var nNew = 0
    var nKeep = 0

    var oldId: Int = 0
    var newId: Int = 0
    while(oldId < oldPairs.length && newId < newPairs.length){
      if(newPairs(newId).key > oldPairs(oldId).key){
        // remove
        oldId += 1
      } else if(newPairs(newId).key == oldPairs(oldId).key){
        assert(nKeep <= oldPairs.length)
        outKeepPairs(nKeep) = oldPairs(oldId)
        nKeep += 1
        oldId += 1
        newId += 1
      }else{
        assert(nNew <= newPairs.length)
        outNewPairs(nNew) = newPairs(newId)
        nNew += 1
        newId += 1
      }
    }

    if(newId < newPairs.length){
      while(newId < newPairs.length){
        assert(nNew <= newPairs.length)
        outNewPairs(nNew) = newPairs(newId)

        nNew += 1
        newId += 1
      }
    }

    ???
  }


  def intersectAABB(centerA: Vector3, halfA: Vector3,
                    centerB: Vector3, halfB: Vector3): Boolean ={
    if((centerA.x - centerB.x).abs > halfA.x + halfB.x) return false
    if((centerA.y - centerB.y).abs > halfA.y + halfB.y) return false
    if((centerA.z - centerB.z).abs > halfA.z + halfB.z) return false

    true
  }
}
