package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.physics._
import jp.ogiwara.sfc.info1.world.sample.Cube
/**
  * World(世界)とは、宇宙である。
  */
@mutable
class World(val systems: Seq[System], val state: WorldState){

  /**
    * 世界の時間を進める
    * 毎フレーム毎に呼び出す。
    */
  def update(): WorldSnapshot = {
    var tmp = state
    for(system <- systems){
      tmp = system.update(tmp)
    }

    tmp.makeSnapShot
  }

}

object NormalWorld extends
  World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq()))

object PrimitiveWorld extends
  World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq(new Cube())))