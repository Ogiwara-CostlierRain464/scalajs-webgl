package jp.ogiwara.sfc.info1.math

import utest._

object QuaternionTest extends TestSuite{
  def tests = Tests {
    'inverse - {
      val result = Quaternion(1,2,3,4).inverse
      assert(result == Quaternion(1,-2,-3,-4))
    }
    '* - {
      val q = Quaternion(1,2,3,4)

      assert(q * q == Quaternion(-28, 4,6,8))
    }
    'byRotate - {
      val q = Quaternion.byRotate(180.rad, Vector3(0,0,1))

      assert(q.z == 1)
    }
  }
}
