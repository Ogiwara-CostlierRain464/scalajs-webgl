package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.math.Vector3
import jp.ogiwara.sfc.info1.mutable
import jp.ogiwara.sfc.info1.physics._
import jp.ogiwara.sfc.info1.render.Position
import jp.ogiwara.sfc.info1.world.sample.Cube
/**
  * World(世界)とは、宇宙である。
  */
@mutable
class World(val systems: Seq[System], var state: WorldState){

  /**
    * 世界の時間を進める
    * 毎フレーム毎に呼び出す。
    */
  def update(): WorldSnapshot = {
    for(system <- systems){
      state = system.update(state)
    }

    state.makeSnapShot()
  }

}

object NormalWorld extends
  World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq()))

object PrimitiveWorld extends
  World(systems = Seq(new NormalPhysicsSystem()), state = WorldState(entities = Seq(new Cube(Position.origin))))