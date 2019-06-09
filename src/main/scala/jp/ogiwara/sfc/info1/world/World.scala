package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.render.View
import jp.ogiwara.sfc.info1.physics._
/**
  * World(世界)とは、宇宙である。
  */
@mutable
class World(val systems: Seq[System], val state: State){

  /**
    * 世界の時間を進める
    * 毎フレーム毎に呼び出す。
    */
  def update(): View = ???

}

object NormalWorld extends World(systems = Seq(new NormalPhysicsSystem()), state = State(entities = Seq()))