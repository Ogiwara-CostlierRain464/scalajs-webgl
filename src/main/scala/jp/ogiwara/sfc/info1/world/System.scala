package jp.ogiwara.sfc.info1.world

/**
  * System(系)とは、宇宙の一部のうち、考察の対象として注目している部分である。
  *
  */
abstract class System {

  /**
    * 現在の状態を受け取って、Systemを介して新たな状態を返す
    */
  abstract def update(state: State): State
}
