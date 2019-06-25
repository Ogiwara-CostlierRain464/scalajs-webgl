package jp.ogiwara.sfc.info1.world

import jp.ogiwara.sfc.info1.mutable
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