package jp.ogiwara.sfc.info1

import utest.{TestSuite, Tests, TestableSymbol}
import Math._

object QuaternionTest extends TestSuite{
  def tests = Tests{
    'rotate - {
      val a = Vector(3,0,0).toQuaternion

      val p = a.rotate(
        180.toRadians,
        Vector(1 / sqrt(2).toFloat, 1 / sqrt(2).toFloat, 0)
      )
    }
  }
}
