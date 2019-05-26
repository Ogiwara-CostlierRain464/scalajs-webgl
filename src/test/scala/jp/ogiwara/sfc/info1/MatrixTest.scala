package jp.ogiwara.sfc.info1

import utest._

object MatrixTest extends TestSuite{
  def tests = Tests {
    '外積 - {
      val i2Times = Matrix.identity + new Matrix(
        1,0,0,0,
        0,1,0,0,
        0,0,1,0,
        0,0,0,1
      )

      val result = i2Times %*% Matrix.identity

      assert(result(0) == 2)
      assert(result(1) == 0)
      assert(result(5) == 2)
      assert(result(10) == 2)
      assert(result(15) == 2)
    }

    '外積2 - {
      val one = Matrix(
        1,1,1,0,
        1,1,1,0,
        0,2,1,0,
        0,0,0,0
      )

      val two = Matrix(
        1,0,1,0,
        0,1,1,0,
        0,0,1,0,
        0,0,0,0
      )

      two %*% one
    }

    '足し算 - {
      val i2Times = Matrix.identity + new Matrix(
        1,0,0,0,
        0,1,0,0,
        0,0,1,0,
        0,0,0,1
      )

      assert(i2Times(0) == 2)
    }
  }
}
