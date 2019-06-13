package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math.Number
import jp.ogiwara.sfc.info1.physics.units._

package object physics {

  implicit def number2phyMeat(number: Number): NumberPhysicsMeta = new NumberPhysicsMeta(number)

  class NumberPhysicsMeta(val body: Number){
    def m: Length = Length(body)
    def kg: Mass = Mass(body)
    def s: Time = Time(body)

    def mPerS: Speed = Speed(body)
    def mPerS2: Acceleration = Acceleration(body)
  }
}