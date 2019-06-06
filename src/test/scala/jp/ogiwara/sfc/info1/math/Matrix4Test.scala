package jp.ogiwara.sfc.info1.math

import utest._

object Matrix4Test extends TestSuite{
  def tests = Tests {
    '× - {
      val m1 = Matrix4(
        1,2,3,4,
        5,6,7,8,
        1,2,3,4,
        5,6,7,8,
      )

      val result = m1 × m1
      assert(result.value._1 == 34)
      assert(result.value._5 == 44)
      assert(result.value._9 == 54)
      assert(result.value._13 == 64)

    }
  }
}
