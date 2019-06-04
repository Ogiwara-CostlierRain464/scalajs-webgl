package jp.ogiwara.sfc.info1

package object math{
  // Application全体で使う数値の型
  type Number = Float

  implicit def double2Float(double: Double): Float = double.toFloat
}
