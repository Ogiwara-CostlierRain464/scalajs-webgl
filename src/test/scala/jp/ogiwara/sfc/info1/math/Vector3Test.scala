package jp.ogiwara.sfc.info1.math

import utest._
import Math._

object Vector3Test extends TestSuite{
  def tests = Tests {
    '× - {
      val actual = Vector3(1,2,3) × Vector3(4,5,6)
      val expect = Vector3(-3,6,-3)

      assert(actual == expect)
    }

    'normalize - {
      val normalized = Vector3(1,2,3).normalized

      assert(normalized.hasNormalized)
    }

    'makeLookAt - {
      val matrix = Vector3(5,5,5).makeLookAt(Vector3(0,0,0))

      assert(round(matrix.value._1 / 0.1) == 7)
    }

    'rotate - {
      val after = Vector3(3,0,0).rotate(90.toRadians, Vector3(0,0,1))

      assert(round(after.y) == 3)
    }
  }
}
