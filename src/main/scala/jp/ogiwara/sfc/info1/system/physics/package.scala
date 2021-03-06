package jp.ogiwara.sfc.info1.system

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units._
import jp.ogiwara.sfc.info1.world.units.{Length, Time}
import Math._

package object physics {

  implicit class NumberPhysicsMeta(val body: Number){

    def kg: Mass = Mass(body)

    def mPerS: Speed = Speed(body)
    def mPerS2: Acceleration = Acceleration(body)

    def N: Force = Force(body)
    def Ns: Momentum = Momentum(body)
  }

  final val G = 9.8f.mPerS2
  final val Gravity = Accelerations(0f.mPerS2, -9.8f.mPerS2, 0f.mPerS2)

}