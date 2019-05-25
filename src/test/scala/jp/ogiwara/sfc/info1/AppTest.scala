package jp.ogiwara.sfc.info1

import utest._

object AppTest extends TestSuite{

  def tests = Tests {
    '外積 - {
      Matrix() %*% Matrix()
    }
  }
}
