package jp.ogiwara.sfc.info1.obj.scanner

import jp.ogiwara.sfc.info1.obj.Obj


trait ObjScanner{
  def applyTo(obj: Obj): Obj
}