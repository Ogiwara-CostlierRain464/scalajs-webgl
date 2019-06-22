package jp.ogiwara.sfc.info1.physics.elements

import jp.ogiwara.sfc.info1.math.{Quaternion, Vector3}
import jp.ogiwara.sfc.info1.render.Mesh

case class Shape(
                  mesh: Mesh,
                  offsetPosition: Vector3,
                  offsetRotation: Quaternion
                )
