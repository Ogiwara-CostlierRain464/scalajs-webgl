package jp.ogiwara.sfc.info1.system

import jp.ogiwara.sfc.info1.math.{Number, Vector3}
import jp.ogiwara.sfc.info1.system.physics.units._

package object physics {

  implicit class NumberPhysicsMeta(val body: Number){
    def m: Length = Length(body)
    def km: Length = Length(body * 1000)

    def kg: Mass = Mass(body)
    def s: Time = Time(body)

    def mPerS: Speed = Speed(body)
    def mPerS2: Acceleration = Acceleration(body)
  }

  final val G = 9.8f.mPerS2
  final val Gravity = Vector3(0, -9.8f, 0)
}