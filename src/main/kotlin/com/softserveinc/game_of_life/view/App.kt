package com.softserveinc.game_of_life.view

import com.softserveinc.game_of_life.controller.OceanController
import com.softserveinc.game_of_life.storage.OceanStorage
import com.softserveinc.game_of_life.service.OceanService
import com.softserveinc.game_of_life.view.frames.GameOfLifeFrame


fun main() {
    val frame = GameOfLifeFrame(OceanController(OceanService(OceanStorage()).also { it.createOcean() }))
    frame.pack()
    frame.isVisible = true
}
