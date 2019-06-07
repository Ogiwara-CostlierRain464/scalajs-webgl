package jp.ogiwara.sfc.info1.render

/**
  * 一つのポリゴンを表す
  */
case class Polygon(tuple: (Vertex, Vertex, Vertex)){
  /**
    * 現在の問題点は、たった一つのポリゴン板を表示するだけでもとんでもない苦労を要する
    * 隠蔽するのが目標
    *
    * パフォーマンスの都合上、手続きてきな操作のほうがいいかも…？
    * OOP + 手続きで。
    * NOT 宣言的。
    *
    * val polygons = new Polygons
    *
    * Screen().
    *
    */

}
