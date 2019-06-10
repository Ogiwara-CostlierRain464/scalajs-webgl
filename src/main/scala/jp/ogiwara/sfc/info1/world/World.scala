package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.physics._
/**
  * World(世界)とは、宇宙である。
  */
@mutable
class World(val systems: Seq[System], val state: WorldState){

  /**
    * 世界の時間を進める
    * 毎フレーム毎に呼び出す。
    */
  def update(): WorldSnapshot = ???

}

object NormalWorld extends
  World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq()))