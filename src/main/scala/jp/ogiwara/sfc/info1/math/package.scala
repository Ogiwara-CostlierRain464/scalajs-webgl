package jp.ogiwara.sfc.info1

import Math._

package object math{
  // Application全体で使う数値の型
  type Number = Float

  implicit def double2Float(double: Double): Float = double.toFloat
  implicit def number2Meta(number: Number): NumberMeta = new NumberMeta(number)
  implicit def int2Meta(int: Int): IntMeta = new IntMeta(int)


  class NumberMeta(val body: Number){
    def ^(rhs: Int): Number ={
      pow(body,rhs)
    }

    def rad: Radians = Radians(body.toRadians)
  }

  class IntMeta(val body: Int){
    def withIn(range: Range): Boolean =
      range.start <= body && body <= range.end

    def rad: Radians = Radians(body.toRadians)
  }

  /**
    * Radian済みの値を格納する
    */
  case class Radians(value: Number){
    def +(rhs: Radians): Radians = Radians(value + rhs.value)
  }
}
