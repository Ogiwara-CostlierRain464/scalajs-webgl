package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.system.physics.RigidBodyId

case class BallJoint(
                      // 拘束の強さの調整値
                      bias: Number,
                      // 剛体Aへのインデックス
                      rigidBodyA: RigidBodyId,
                      // 剛体Bへのインデックス
                      rigidBodyB: RigidBodyId,
                      // 剛体Aのローカル座標系における接続点
                      anchorA: Vector3 = Vector3(0,0,0),
                      // 剛体Bのローカル座標系における接続点
                      anchorB: Vector3 = Vector3(0,0,0),
                      // 拘束
                      constraint: Constraint = Constraint()){
}
