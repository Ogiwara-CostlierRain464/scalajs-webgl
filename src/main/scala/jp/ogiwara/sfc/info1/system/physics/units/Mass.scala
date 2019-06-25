package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Number

case class Mass(kg: Number){
  override def toString: String = s"${kg}kg"
}
