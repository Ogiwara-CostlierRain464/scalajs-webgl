import utest._

object TypeTest extends TestSuite{
  override def tests: Tests = {
    'N - {
      sealed trait Nat
      final class _0 extends Nat
      final class Succ[P <: Nat]() extends Nat

      type _1 = Succ[_0]
      type _2 = Succ[_1]
      type _3 = Succ[_2]
      type _4 = Succ[_3]
      type _5 = Succ[_4]

      trait Sum[A <: Nat, B <: Nat] { type Out <: Nat }

      object Sum {
        implicit def sum1[B <: Nat]: Aux[_0, B, B] = new Sum[_0, B]{
          type Out = B
        }

        def apply[A <: Nat, B <: Nat](implicit sum: Sum[A,B]): Aux[A,B, sum.Out] = sum

        type Aux[A <: Nat, B <: Nat, C <: Nat] = Sum[A,B]{
          type Out = C
        }

        implicit def sum2[A <: Nat, B <: Nat](implicit sum: Sum[A, Succ[B]]): Aux[Succ[A], B, sum.Out] = new Sum[Succ[A], B]{
          type Out = sum.Out
        }

      }
    }
  }
}