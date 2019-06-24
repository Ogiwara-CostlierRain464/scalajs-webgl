package jp.ogiwara.sfc

import scala.language.implicitConversions
import scala.language.reflectiveCalls


package object info1 {

  /**
    * 状態を持つ、ミュータブルなインスタンスにマークする
    */
  class mutable

  /**
    * フィールドが遅れて初期化されることを表す
    */
  class lateInit

  implicit def Pipeline[T](x: T): Object {def |>[S](f: T => S): S} = new {
    def |>[S](f: T => S): S = f(x)
  }
}