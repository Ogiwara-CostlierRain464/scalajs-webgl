package jp.ogiwara.sfc.info1

import utest._

object AppTest extends TestSuite{

  def tests = Tests {
    'HelloWorld - {
      assert(1 + 1 == 2)
    }
  }
}
