package jp.ogiwara.sfc.info1

import java.lang.Math.sqrt

import utest._

object VectorTest extends TestSuite{
  def tests = Tests {
    'ノルム - {
      assert(Vector(1,2,3).norm.round == 4)
    }
    '正規化 - {
      assert(Vector(1,2,3).normalize.norm.round == 1)
    }
    'rotate - {
      val p = Vector(3,0,0).toQuaternion.rotate(
        180.toRadians,
        Vector(1 / sqrt(2).toFloat, 1 / sqrt(2).toFloat, 0)
      )

      Vector(3,0,0).rotate(by = p)
    }
  }
}
