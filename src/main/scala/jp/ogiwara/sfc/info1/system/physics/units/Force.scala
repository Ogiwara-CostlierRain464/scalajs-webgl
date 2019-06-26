package jp.ogiwara.sfc.info1.system.physics.units

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.world.units.Time

case class Force(N: Number){
  // f = ma -> f/m = a
  def /(kg: Mass): Acceleration = Acceleration(N / kg.kg)
  // N * s -> P
  def *(second: Time): Momentum = Momentum(N * second.second)
}
