package com.softserveinc.game_of_life.models

import com.softserveinc.game_of_life.utils.Constants

class Predator(
    private var timeToReproduce: Int = Constants.randomTimeToReproduce(),
    private var timeToFeed: Int = Constants.randomDefaultTimeToFeed(),
    private var alive: Boolean = true,
    ocean: Ocean,
    coordinate: Coordinate
) : CellBehavior(ocean, coordinate){

    override fun getDefaultImage() = "🦈"

    override fun process() {
        iteration()

        if (!alive) {
            die()
            return
        }

        if (!eat()) {
            moveRandom()
        }

        if (timeToReproduce <= 0) {
            reproduce()
            timeToReproduce = Constants.randomTimeToReproduce()
        }

    }

    private fun eat(): Boolean {
        return neighbors.shuffled().map { stabilizationCoordinate(it + coordinate) }
            .firstOrNull { ocean.ocean!![it.row][it.colum] is Prey }
            ?.let {
                changeCoordinate(it)
                timeToFeed = Constants.randomDefaultTimeToFeed()
                true
            } ?: false
    }

    override fun createNewEntity(coordinate: Coordinate) = Predator(
        ocean = ocean,
        coordinate = coordinate
    )

    private fun iteration() {
        timeToFeed--
        timeToReproduce--

        if (timeToFeed <= 0) {
            alive = false
        }
    }
}
