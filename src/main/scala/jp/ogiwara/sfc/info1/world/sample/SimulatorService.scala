package jp.ogiwara.sfc.info1.world.sample

import jp.ogiwara.sfc.info1.math._
import jp.ogiwara.sfc.info1.system.physics._
import jp.ogiwara.sfc.info1.system.physics.units.Speed
import jp.ogiwara.sfc.info1.render.Position
import Math._

object SimulatorService {
  def cannon(θx: Radians,
             θy: Radians,
             θz: Radians, vm: Speed): RigidBody ={

    val ax = 0f.mPerS2
    // 初期
    val vx = vm * cos(θx.value)

    val ay = -G
    // 初期
    val vy = vm * cos(θy.value)

    val az = 0f.mPerS2
    val vz = vm * cos(θz.value)



    /*

    RigidBody(
      position = Position(0f.m, 0f.m, 0f.m),
      mass = 1f.kg,
      speed = Speeds(vx, vy, vz),
      accelerations = Accelerations(ax, ay, az)
    )
    */
    ???
  }
}
