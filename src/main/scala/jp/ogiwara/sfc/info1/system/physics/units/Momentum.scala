package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Number

/**
  * 運動量
  */
case class Momentum(Ns: Number){
  def +(rhs: Momentum): Momentum = Momentum(Ns + rhs.Ns)
}
