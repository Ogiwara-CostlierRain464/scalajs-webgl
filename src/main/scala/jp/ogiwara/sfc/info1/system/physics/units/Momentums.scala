package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Vector3

case class Momentums(x: Momentum, y: Momentum, z: Momentum){
  def vector: Vector3 = Vector3(x.Ns, y.Ns, z.Ns)

  def +(rhs: Momentums): Momentums = Momentums(x + rhs.x, y + rhs.y, z + rhs.z)
}
