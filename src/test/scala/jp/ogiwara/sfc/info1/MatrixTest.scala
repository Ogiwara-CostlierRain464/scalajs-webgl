package jp.ogiwara.sfc.info1

import utest._

object MatrixTest extends TestSuite{
  def tests = Tests {
    '外積 - {
      val i2Times = Matrix() + new Matrix(
        1,0,0,0,
        0,1,0,0,
        0,0,1,0,
        0,0,0,1
      )

      val result = i2Times %*% Matrix()

      assert(result(0) == 2)
      assert(result(5) == 2)
      assert(result(10) == 2)
      assert(result(15) == 2)
    }

    '足し算 - {
      val i2Times = Matrix() + new Matrix(
        1,0,0,0,
        0,1,0,0,
        0,0,1,0,
        0,0,0,1
      )

      assert(i2Times(0) == 2)
    }
  }
}
