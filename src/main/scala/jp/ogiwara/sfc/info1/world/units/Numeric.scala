package jp.ogiwara.sfc.info1.world.units

import jp.ogiwara.sfc.info1.math.Number

/**
  * Maybe this would'n work.
  */
trait Numeric[Type <: { def apply(body: Number): Type }]{
  val body: Number

}