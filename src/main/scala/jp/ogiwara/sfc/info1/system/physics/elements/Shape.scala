package jp.ogiwara.sfc.info1.system.physics.elements

import jp.ogiwara.sfc.info1.math.{Quaternion, Vector3}
import jp.ogiwara.sfc.info1.world.Mesh

case class Shape(
                  mesh: Mesh,
                  offsetPosition: Vector3,
                  offsetRotation: Quaternion
                )
