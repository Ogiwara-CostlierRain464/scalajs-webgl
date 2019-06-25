package jp.ogiwara.sfc.info1.system

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units._
import jp.ogiwara.sfc.info1.world.units.{Length, Time}

package object physics {

  implicit class NumberPhysicsMeta(val body: Number){

    def kg: Mass = Mass(body)

    def mPerS: Speed = Speed(body)
    def mPerS2: Acceleration = Acceleration(body)
  }

  final val G = 9.8f.mPerS2
  final val Gravity = Vector3(0, -9.8f, 0)
}