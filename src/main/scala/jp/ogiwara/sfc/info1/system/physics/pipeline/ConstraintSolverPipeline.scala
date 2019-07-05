package jp.ogiwara.sfc.info1.system.physics.pipeline

import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.system.physics.Bodies
import jp.ogiwara.sfc.info1.system.physics.elements.{BallJoint, CollisionPair}

object ConstraintSolverPipeline {
  def apply(
             bodies: Bodies,
             pairs: Seq[CollisionPair],
             joints: Seq[BallJoint],
             // 計算の反復回数
             iteration: Int,
             // 位置補正のバイアス
             bias: Number,
             // 貫通許容誤差
             slop: Number,
             timeStep: Number
           ): Unit ={

  }
}
