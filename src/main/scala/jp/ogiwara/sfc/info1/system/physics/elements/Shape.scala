package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Quaternion, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units.{LocalPosition, ShapeLocalPosition}
import jp.ogiwara.sfc.info1.world.{Mesh, Rotation}

case class Shape(
                  mesh: Mesh,
                  // オフセット(ローカル)座標
                  offsetPosition: ShapeLocalPosition,
                  offsetRotation: Rotation
                )
