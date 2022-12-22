package com.softserveinc.game_of_life.models

import com.softserveinc.game_of_life.utils.Constants

class Prey(
    private var timeToReproduce: Int = Constants.randomTimeToReproduce(),
    ocean: Ocean,
    coordinate: Coordinate,
) : CellBehavior(ocean, coordinate) {

    override fun getDefaultImage() = "🐡"

    override fun process() {
        decrementTimeToReproduce()
        moveRandom()
        if (timeToReproduce <= 0) {
            reproduce()
            timeToReproduce = Constants.randomTimeToReproduce()
        }
    }

    private fun decrementTimeToReproduce() {
        timeToReproduce--
    }

    override fun createNewEntity(coordinate: Coordinate): Cell {
        return Prey(ocean = ocean, coordinate = coordinate)
    }
}
