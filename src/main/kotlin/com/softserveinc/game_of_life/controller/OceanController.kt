package com.softserveinc.game_of_life.controller

import com.softserveinc.game_of_life.models.Ocean
import com.softserveinc.game_of_life.models.OceanEntity
import com.softserveinc.game_of_life.service.OceanService

class OceanController(private val service: OceanService) {

    fun getOceanEntity(): OceanEntity {
        return service.getOceanEntity()
    }

    fun getOcean(): Ocean {
        return service.getOcean()
    }

    fun iterate(): OceanEntity {
        service.iteration(0)
        return service.getOceanEntity()
    }

    fun output(): String {
        return service.output()
    }

    fun reset() {
        service.reset()
    }
}
