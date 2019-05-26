package jp.ogiwara.sfc.info1

import utest._

object VectorTest extends TestSuite{
  def tests = Tests {
    'ノルム - {
      assert(Vector(1,2,3).norm.round == 4)
    }
    '正規化 - {
      assert(Vector(1,2,3).normalize.norm.round == 1)
    }
  }
}
