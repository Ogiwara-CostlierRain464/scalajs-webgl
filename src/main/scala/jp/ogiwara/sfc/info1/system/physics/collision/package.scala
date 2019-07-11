package jp.ogiwara.sfc.info1.system.physics

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.system.physics.elements.Transform
import jp.ogiwara.sfc.info1.world.{Convex, Mesh}
import jp.ogiwara.sfc.info1.math._

import util.control.Breaks._

package object collision {
  /**
    * 衝突の組み合わせ。
    * 最終的にこのように判定を削れる理由については、P210ら辺を確認
    */
  sealed trait SatType
  // 頂点 対 面
  case object PointA2FacetB extends SatType
  case object PointB2FacetA extends SatType
  // 辺 対 辺
  case object Edge2Edge extends SatType


  /**
    * 凸形状の衝突判定
    *
    * 衝突を検知した場合はSome、そうでないときはNoneを返す
    */
  def convexContact(meshA: Mesh, transformA: Transform,
                    meshB: Mesh, transformB: Transform): Option[(Vector3, Number, Vector3, Vector3)] ={
    // 座標系変換の回数を減らすため、面数の多い方を座標系の基準にとる

    if(meshA.facets.length < meshB.facets.length)
      convexContact(meshB, transformB, meshA, transformA)

    // B local -> A localへの変換
    val transformAB = transformA.inverse × transformB
    val matrixAB = transformAB.upper3x3
    val offsetAB = transformAB.translation

    // A local -> B localへの変換
    val transformBA = transformAB.inverse
    val matrixBA = transformBA.upper3x3
    val offsetBA = transformBA.translation

    // 最も浅い貫通深度とそのときの分離軸
    var distanceMin = -Float.MaxValue
    var axisMin = Vector3(0,0,0)
    var satType: SatType = Edge2Edge
    var axisFlip = false

    //region 分離軸判定
    var satCount = 0

    // ConvexAの面法線を分離軸とする
    for(facet <- meshA.facets){
      val separatingAxis = facet.normal

      // ConvexAを分離軸に投影
      val (minA, maxA) = getProjection(meshA, separatingAxis)

      // ConvexBを分離軸に投影
      var (minB, maxB) = getProjection(meshB, matrixBA × facet.normal)
      val offset = offsetAB * separatingAxis

      minB += offset
      maxB += offset

      //CHECK_MIN_MAX
      satCount += 1
      val d1 = minA - maxB
      val d2 = minB - maxA

      if(d1 >= 0f || d2 >= 0f){
        return None
      }
      if(distanceMin < d1){
        distanceMin = d1
        axisMin = separatingAxis
        satType = PointB2FacetA
        axisFlip = false
      }
      if(distanceMin < d2){
        distanceMin = d2
        axisMin = -separatingAxis
        satType = PointB2FacetA
        axisFlip = true
      }
    }

    // ConvexBの面法線を分離軸とする
    for(facet <- meshB.facets){
      val separatingAxis = matrixAB × facet.normal

      val (minA, maxA) = getProjection(meshA, separatingAxis)
      var (minB, maxB) = getProjection(meshB, facet.normal)
      val offset = offsetAB * separatingAxis

      minB += offset
      maxB += offset

      //CHECK_MIN_MAX
      satCount += 1
      val d1 = minA - maxB
      val d2 = minB - maxA

      if(d1 >= 0f || d2 >= 0f){
        return None
      }
      if(distanceMin < d1){
        distanceMin = d1
        axisMin = separatingAxis
        satType = PointA2FacetB
        axisFlip = false
      }
      if(distanceMin < d2){
        distanceMin = d2
        axisMin = -separatingAxis
        satType = PointA2FacetB
        axisFlip = true
      }
    }

    // ConvexAとConvexBのエッジの外積を分離軸とする
    for(edgeA <- meshA.edges){
      breakable {
        if(edgeA.aType != Convex) break

        val edgeVecA = meshA.vertexes(edgeA.vertexIds._2) - meshA.vertexes(edgeA.vertexIds._1)

        for(edgeB <- meshB.edges){
          breakable {
            if(edgeB.aType != Convex) break

            val edgeVecB = matrixAB × (meshB.vertexes(edgeB.vertexIds._2) - meshB.vertexes(edgeB.vertexIds._1))

            var separatingAxis = edgeVecA × edgeVecB

            if(separatingAxis.norm.sqrt < (1e-5f ^ 2)) break

            separatingAxis = separatingAxis.normalized

            val (minA, maxA) = getProjection(meshA, separatingAxis)
            var (minB, maxB) = getProjection(meshB, matrixBA × separatingAxis)
            val offset = offsetAB * separatingAxis

            minB += offset
            maxB += offset

            //CHECK_MIN_MAX
            satCount += 1
            val d1 = minA - maxB
            val d2 = minB - maxA

            if(d1 >= 0f || d2 >= 0f){
              return None
            }
            if(distanceMin < d1){
              distanceMin = d1
              axisMin = separatingAxis
              satType = Edge2Edge
              axisFlip = false
            }
            if(distanceMin < d2){
              distanceMin = d2
              axisMin = -separatingAxis
              satType = Edge2Edge
              axisFlip = true
            }
          }
        }
      }
    }

    //endregion

    //ここまで処理が到達したので、２つの凸メッシュは交差している。
    // また、反発ベクトル(axisMin)と貫通深度(distanceMin)が求まった。
    // 反発ベクトルはＡを押しだす方向をプラスにとる。

    //region 衝突座標検出
    var collCount = 0
    var closestMinSqr = Float.MaxValue
    var closestPointA = Vector3(0,0,0)
    var closestPointB = Vector3(0,0,0)
    val separation = axisMin * distanceMin.abs * 1.1f

    for(facetA <- meshA.facets){
      breakable {
        val checkA = facetA.normal * (-axisMin)
        if(satType == PointB2FacetA && checkA < 0.99f && axisFlip){
          // 判定軸が面Aの法線のとき、向きの違うAの面は判定しない
          break
        }
        if(checkA < 0.0f){
          // 衝突面と逆に向いている面は判定しない
          break
        }

        for(facetB <- meshB.facets){
          breakable {
            val checkB = facetA.normal * (matrixBA × axisMin)
            if(satType == PointA2FacetB && checkB < 0.99f && !axisFlip){
              // 判定軸が面Bの法線のとき、向きの違うBの面は判定しない
              break
            }
            if(checkB < 0.0f){
              // 衝突面と逆に向いている面は判定しない
              break
            }

            collCount += 1

            val triangleA = Seq(
              separation + meshA.vertexes(facetA.vertexIds._1).position.vector,
              separation + meshA.vertexes(facetA.vertexIds._2).position.vector,
              separation + meshA.vertexes(facetA.vertexIds._3).position.vector,
            )

            val triangleB = Seq(
              offsetAB + (matrixAB × meshB.vertexes(facetB.vertexIds._1).position.vector),
              offsetAB + (matrixAB × meshB.vertexes(facetB.vertexIds._2).position.vector),
              offsetAB + (matrixAB × meshB.vertexes(facetB.vertexIds._3).position.vector),
            )

            // エッジ同士の最近接点算出
            for(i <- 0 to 2){
              breakable {
                if(meshA.edges(facetA.edgeIds.productElement(i).asInstanceOf[Int]).aType != Convex) break

                for(j <- 0 to 2){
                  breakable {
                    if(meshB.edges(facetB.edgeIds.productElement(j).asInstanceOf[Int]).aType != Convex) break

                    val (sA, sB) = getClosestTwoSegments(
                      triangleA(i), triangleA((i+1)%3),
                      triangleB(j), triangleB((j+1)%3)
                    )

                    val dSqr = (sA - sB).norm.sqrt
                    if(dSqr < closestMinSqr){
                      closestMinSqr = dSqr
                      closestPointA = sA
                      closestPointB = sB
                    }
                  }
                }
              }
            }

            // 頂点Ａ→面Ｂの最近接点算出
            for(i <- 0 to 2){
              val s = getClosestPointTriangle(triangleA(i), triangleB(0), triangleB(1), triangleB(2), matrixAB × facetB.normal)
              val dSqr = (triangleA(i) - s).norm.sqrt
              if(dSqr < closestMinSqr){
                closestMinSqr = dSqr
                closestPointA = triangleA(i)
                closestPointB = s
              }
            }

            // 頂点Ｂ→面Ａの最近接点算出
            for(i <- 0 to 2){
              val s = getClosestPointTriangle(triangleB(i), triangleA(0), triangleA(1), triangleA(2), facetA.normal)
              val dSqr = (triangleB(i) - s).norm.sqrt
              if(dSqr < closestMinSqr){
                closestMinSqr = dSqr
                closestPointA = s
                closestPointB = triangleB(i)
              }
            }
          }
        }
      }
    }

    val normal = transformA.upper3x3 × axisMin
    val penetrationDepth = distanceMin
    val contactPointA = closestPointA - separation
    val contactPointB = offsetBA + (matrixBA × closestPointB)

    Some((normal, penetrationDepth, contactPointA, contactPointB))
  }

  /**
    * 軸上に凸メッシュを投影し、最小値と最大値を得る
    */
  def getProjection(mesh: Mesh, axis: Vector3): (Float, Float) ={
    var pMin = Float.MaxValue
    var pMax = -Float.MaxValue

    for(vertex <- mesh.vertexes){
      val projection = axis * vertex.position.vector
      pMin = Math.min(pMin, projection)
      pMax = Math.max(pMax, projection)
    }

    (pMin, pMax)
  }

  /**
    * 二つの線分の最近接点検出
    */
  def getClosestTwoSegments(segmentPointA0: Vector3, segmentPointA1: Vector3,
                            segmentPointB0: Vector3, segmentPointB1: Vector3): (Vector3, Vector3) ={
    val v1 = segmentPointA1 - segmentPointA0
    val v2 = segmentPointB1 - segmentPointB0
    val r = segmentPointA0 - segmentPointB0

    val a = v1 * v1
    val b = v1 * v2
    val c = v2 * v2
    val d = v1 * r
    val e = v2 * r
    val det = -a*c+b*b
    var s = 0f
    var t =0f

    // 逆行列の存在をチェック
    if((det^2) > 1e-5f){
      s = (c*d-b*e)/det
    }

    // 線分A上の最近接点を決めるパラメータsを0.0～1.0でクランプ
    s = s.clamp(0,1)

    // 線分Bのtを求める
    //t = dot((segmentPointA0+s*v1) - segmentPointB0,v2) / dot(v2,v2);
    t = (e+s*b)/c

    // 線分B上の最近接点を決めるパラメータtを0.0～1.0でクランプ
    t = t.clamp(0,1)

    // 再度、線分A上の点を求める
    //s = dot((segmentPointB0+t*v2) - segmentPointA0,v1) / dot(v1,v1);
    s = (-d+t*b)/a
    s = s.clamp(0,1)

    val closestPointA = segmentPointA0 + (v1 * s)
    val closestPointB = segmentPointB0 + (v2 * t)

    (closestPointA, closestPointB)
  }

  /**
    * 頂点から３角形面への最近接点検出
    */
  def getClosestPointTriangle(
                               // 頂点
                               point: Vector3,
                               // 3角形面の頂点0
                               trianglePoint0: Vector3,
                               // 3角形面の頂点1
                               trianglePoint1: Vector3,
                               // 3角形面の頂点2
                               trianglePoint2: Vector3,
                               // 3角形面の法線ベクトル
                               triangleNormal: Vector3): Vector3 ={

    // 3角形面上の投影点
    val projection = point - (triangleNormal * (triangleNormal * (point - trianglePoint0)))

    // エッジP0,P1のボロノイ領域
    val edgeP01 = trianglePoint1 - trianglePoint0
    val edgeP01normal = edgeP01 × triangleNormal

    val voronoiEdgeP01check1 = (projection - trianglePoint0) * edgeP01normal
    val voronoiEdgeP01check2 = (projection - trianglePoint0) * edgeP01
    val voronoiEdgeP01check3 = (projection - trianglePoint1) * (-edgeP01)

    if(voronoiEdgeP01check1 > 0 && voronoiEdgeP01check2 > 0 && voronoiEdgeP01check3 > 0){
      return getClosestPointLine(trianglePoint0, edgeP01, projection)
    }

    // エッジP1,P2のボロノイ領域
    val edgeP12 = trianglePoint2 - trianglePoint1
    val edgeP12normal = edgeP12 × triangleNormal

    val voronoiEdgeP12check1 = (projection - trianglePoint1) * edgeP12normal
    val voronoiEdgeP12check2 = (projection - trianglePoint1) * edgeP12
    val voronoiEdgeP12check3 = (projection - trianglePoint2) * (-edgeP12)

    if(voronoiEdgeP12check1 > 0 && voronoiEdgeP12check2 > 0 && voronoiEdgeP12check3 > 0){
      return getClosestPointLine(trianglePoint1, edgeP12, projection)
    }

    // エッジP2,P0のボロノイ領域
    val edgeP20 = trianglePoint0 - trianglePoint2
    val edgeP20normal = edgeP20 × triangleNormal

    val voronoiEdgeP20check1 = (projection - trianglePoint2) * edgeP20normal
    val voronoiEdgeP20check2 = (projection - trianglePoint2) * edgeP20
    val voronoiEdgeP20check3 = (projection - trianglePoint0) * (-edgeP20)

    if(voronoiEdgeP20check1 > 0 && voronoiEdgeP20check2 > 0 && voronoiEdgeP20check3 > 0){
      return getClosestPointLine(trianglePoint2, edgeP20, projection)
    }

    // 3角形面の内側
    if(voronoiEdgeP01check1 <= 0 && voronoiEdgeP12check1 <= 0 && voronoiEdgeP20check1 <= 0){
      return projection
    }

    // 頂点P0のボロノイ領域
    if(voronoiEdgeP01check2 <= 0 && voronoiEdgeP20check3 <= 0){
      return trianglePoint0
    }

    // 頂点P1のボロノイ領域
    if(voronoiEdgeP01check3 <= 0 && voronoiEdgeP12check2 <= 0){
      return trianglePoint1
    }

    // 頂点P2のボロノイ領域
    if(voronoiEdgeP20check2 <= 0 && voronoiEdgeP12check3 <= 0){
      return trianglePoint2
    }

    Vector3(0,0,0)
  }

  def getClosestPointLine(point: Vector3,
                          linePoint: Vector3,
                          lineDirection: Vector3
                         ): Vector3 ={
    val s = ((point - linePoint)*lineDirection) / (lineDirection * lineDirection)
    val closestPoint = linePoint + lineDirection * s

    closestPoint
  }
}
