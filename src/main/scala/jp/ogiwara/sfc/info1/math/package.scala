package jp.ogiwara.sfc.info1

import Math._

package object math{
  // Application全体で使う数値の型
  type Number = Float

  implicit def double2Float(double: Double): Float = double.toFloat

  implicit def number2Meta(number: Number): NumberMeta = new NumberMeta(number)

  class NumberMeta(val body: Number){
    def ^(rhs: Int): Number ={
      pow(body,rhs)
    }
  }
}
