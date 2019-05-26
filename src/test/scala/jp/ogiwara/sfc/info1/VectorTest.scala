package jp.ogiwara.sfc.info1

import utest._

object VectorTest extends TestSuite{
  def tests = Tests {
    'ノルム - {
      assert(Math.round( Vector(1,2,3).norm) == 4)
    }
  }
}
