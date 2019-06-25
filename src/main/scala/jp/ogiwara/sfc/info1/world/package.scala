package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.world.units.{Length, Time}

package object world {

  implicit class NumberWorldMeta(val body: Number){
    def m: Length = Length(body)
    def km: Length = Length(body * 1000)

    def s: Time = Time(body)
  }
}
