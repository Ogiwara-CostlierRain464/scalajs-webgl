package tutorial.webapp

import utest._

object TutorialTest extends TestSuite{

  def tests = Tests {
    'HelloWorld - {
      assert(1 + 1 == 2)
    }
  }
}
