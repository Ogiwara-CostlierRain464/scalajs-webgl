package jp.ogiwara.sfc.info1.render.obj.scanner

import jp.ogiwara.sfc.info1.render.obj.Obj

sealed class CommentObjScanner(val line: String) extends ObjScanner{
  override def applyTo(obj: Obj): Obj = {
    // 何もしない
    obj
  }
}