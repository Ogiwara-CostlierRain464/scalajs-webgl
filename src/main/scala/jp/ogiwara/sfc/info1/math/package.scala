package jp.ogiwara.sfc.info1

package object math{
  type Number = Float

  implicit def double2Float(double: Double): Float = double.toFloat
}
