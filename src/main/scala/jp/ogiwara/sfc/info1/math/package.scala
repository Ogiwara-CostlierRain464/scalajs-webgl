package jp.ogiwara.sfc.info1

import Math._

package object math{
  // Application全体で使う数値の型
  type Number = Float

  implicit def double2Float(double: Double): Float = double.toFloat
  implicit def int2Number(int: Int): Number = int.toFloat

  implicit class NumberMeta(val body: Number){
    def ^(rhs: Int): Number ={
      pow(body,rhs)
    }

    def rad: Radians = Radians(body.toRadians)

    def clamp(from: Number, to: Number): Number =
      max(from, min(body, to))

    def sqrt: Number = Math.sqrt(body.toDouble)
  }

  implicit class IntMeta(val body: Int){
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
