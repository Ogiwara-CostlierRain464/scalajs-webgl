package jp.ogiwara.sfc.info1.render.obj.scanner

import jp.ogiwara.sfc.info1.render.obj.Obj


trait ObjScanner{
  def applyTo(obj: Obj): Obj
}