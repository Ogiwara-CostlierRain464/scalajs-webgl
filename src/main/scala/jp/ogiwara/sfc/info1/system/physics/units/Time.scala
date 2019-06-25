package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math.Number

case class Time(second: Number){
  override def toString: String = s"${second}s"
}
