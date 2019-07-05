package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.Number

// 衝突情報
case class Contact(
                    // 衝突の数
                    count: Int = 0,
                    // 摩擦
                    friction: Number = 0,
                    // 衝突点の配列
                    contactPoints: Array[ContactPoint] = new Array[ContactPoint](4)
                  ){

  def findNearestContactPoint(): Unit ={
    var nearestIdx = -1

    val minDiff = 0.01f


  }

  def addContact(): Unit ={

  }
}