package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.mutable
import Math._

// 衝突情報
@mutable
case class Contact(
                    // 衝突の数
                    var count: Int = 0,
                    // 摩擦
                    var friction: Number = 0,
                    // 衝突点の配列
                    contactPoints: Array[ContactPoint] = Array(ContactPoint(), ContactPoint(), ContactPoint(), ContactPoint())
                  ){

  override def toString: String = s"(${contactPoints(0)} ${contactPoints(1)} ${contactPoints(2)} ${contactPoints(3)})"

  /**
    * 同一衝突点を探索する
    * 同じ衝突点を見つけた場合はそのインデックスを返す
    * 見つからなかった場合は-1を返す
    */
  def findNearestContactPoint(
                               // 衝突点（剛体Aのローカル座標系）
                               newPointA: Vector3,
                               // 衝突点（剛体Bのローカル座標系）
                               newPointB: Vector3,
                               // 衝突点の法線ベクトル（ワールド座標系）
                               newNormal: Vector3): Int ={
    var nearestIdx = -1

    var minDiff = 0.01f
    for(i <- 0 until count){
      val diffA = (contactPoints(i).pointA - newPointA).norm
      val diffB = (contactPoints(i).pointB - newPointB).norm

      if(diffA < minDiff && diffB < minDiff && newNormal * contactPoints(i).normal > 0.99){
        minDiff = Math.max(diffA, diffB)
        nearestIdx = i
      }
    }

    nearestIdx
  }

  /**
    * 衝突点を入れ替える
    * @param newPoint 衝突点
    * @param newDistance 貫通深度
    * @return 破棄する衝突点のインデックスを返す
    */
  def sort4ContactPoints(newPoint: Vector3, newDistance: Number): Int ={
    var maxPenetrationIndex = -1
    var maxPenetration = newDistance

    for(i <- 0 until count){
      if(contactPoints(i).distance < maxPenetration){
        maxPenetrationIndex = i
        maxPenetration = contactPoints(i).distance
      }
    }

    val res: Array[Float] = new Array[Number](4)
    res(0) = 0

    // 各点を除いたときの衝突点が作る面積のうち、最も大きくなるものを選択
    val p = Seq(
      contactPoints(0).pointA,
      contactPoints(1).pointA,
      contactPoints(2).pointA,
      contactPoints(3).pointA,
    )

    if(maxPenetrationIndex != 0){
      res(0) = calcArea4Points(newPoint, p(1), p(2), p(3))
    }
    if(maxPenetrationIndex != 1){
      res(1) = calcArea4Points(newPoint, p(0), p(2), p(3))
    }
    if(maxPenetrationIndex != 2){
      res(2) = calcArea4Points(newPoint, p(0), p(1), p(3))
    }
    if(maxPenetrationIndex != 3){
      res(3) = calcArea4Points(newPoint, p(0), p(1), p(2))
    }

    var maxIndex = 0
    var maxVal = res(0)

    if(res(1) > maxVal){
      maxIndex = 1
      maxVal = res(1)
    }
    if(res(2) > maxVal){
      maxIndex = 2
      maxVal = res(2)
    }
    if(res(3) > maxVal){
      maxIndex = 3
      maxVal = res(3)
    }

    maxIndex
  }


  def calcArea4Points(p0: Vector3, p1: Vector3, p2: Vector3, p3: Vector3): Number ={
    val areaSqrA = ((p0-p1) × (p2 - p3)).norm
    val areaSqrB = ((p0-p2) × (p1 - p3)).norm
    val areaSqrC = ((p0-p3) × (p1 - p2)).norm

    max(areaSqrC, max(areaSqrA, areaSqrB))
  }

  /**
    * 衝突点を追加する
    */
  def addContact(
                  // 貫通深度
                  penetrationDepth: Number,
                  // 衝突点の法線ベクトル（ワールド座標系）
                  normal: Vector3,
                  // 衝突点（剛体Aのローカル座標系）
                  contactPointA: Vector3,
                  // 衝突点（剛体Bのローカル座標系）
                  contactPointB: Vector3): Unit ={
    var id = findNearestContactPoint(contactPointA, contactPointB, normal)

    if(id < 0 && count < 4){
      id = count
      count += 1
      contactPoints(id).reset()
    }else if(id < 0){
      id = sort4ContactPoints(contactPointA, penetrationDepth)

      contactPoints(id).reset()
    }

    contactPoints(id).distance = penetrationDepth
    contactPoints(id).pointA = contactPointA
    contactPoints(id).pointB = contactPointB
    contactPoints(id).normal = normal
  }
}