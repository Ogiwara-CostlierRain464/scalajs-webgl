package jp.ogiwara.sfc.info1.system.physics

import utest.{TestSuite, Tests, TestableSymbol}

object UniTest extends TestSuite{
  def tests = Tests {
    '加算可能 - {

      trait Addable[Unit]{
        def +(rhs: Unit): Unit
      }

      case class Length(m: Float) extends Addable[Length]{
        def +(rhs: Length): Length = {
          Length(m + rhs.m)
        }
      }

      case class V2[Unit <: Addable[Unit]](one: Unit, two: Unit){
        def +(rhs: V2[Unit]): V2[Unit] ={
          V2(
            one + rhs.one,
            two + rhs.two
          )
        }
      }

      val v = V2(Length(1), Length(2))
      val v2 = V2(Length(1), Length(2))

      v + v2

    }
  }
}

class ::{

}