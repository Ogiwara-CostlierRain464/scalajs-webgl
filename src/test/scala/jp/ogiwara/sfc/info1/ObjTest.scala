package jp.ogiwara.sfc.info1

import jp.ogiwara.sfc.info1.obj.Obj
import utest._

object ObjTest extends TestSuite{

  def tests = Tests {
    '読み込み - {
      Obj.fromText(
        """
          |# this is comment
          |v 0.5 0.577 0.5
          |v 0.5 1.732 0
          |v 0 0 0
          |v 1 0 0
          |vt 1 2
          |f 1 1 1
        """.stripMargin)
    }
  }
}
