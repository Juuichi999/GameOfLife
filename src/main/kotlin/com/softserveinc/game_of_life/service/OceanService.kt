package com.softserveinc.game_of_life.service

import com.softserveinc.game_of_life.models.*
import com.softserveinc.game_of_life.storage.OceanStorage
import kotlin.random.Random

class OceanService(
    private val repository: OceanStorage,
) {

    fun getOcean(): Ocean {
        return repository.oceans
    }

    fun getOceanEntity(): OceanEntity {
        return repository.oceans.toOceanEntity()
    }

    fun createOcean(
        row: Int = 25,
        colum: Int = 70,
        numberPrey: Int = 150,
        numberPredator: Int = 25,
        numberObstacle: Int = 75,
        random: Random = Random.Default,
    ): Ocean {
        val ocean = Ocean()

        ocean.ocean = Array(row) { currentRow ->
            Array(colum) { currentColum ->
                Empty(
                    ocean,
                    Coordinate(currentRow, currentColum)
                )
            }
        }
        if (row < 1 || colum < 1 || numberPrey < 0 || numberPredator < 0 || numberObstacle < 0) {
            throw IllegalArgumentException()
        }

        val cells = List(row * colum) { Coordinate(it / colum, it % colum) }
        val shuffledCells = cells.shuffled(random)

        var temp = numberObstacle
        var i = 0
        while (temp > 0) {
            val cell = ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum]
            if (cell !is Obstacle) {
                ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum] = Obstacle(
                    ocean = ocean,
                    coordinate = Coordinate(shuffledCells[i].row, shuffledCells[i].colum)
                )
                temp--
            }
            i++
        }

        temp = numberPrey
        while (temp > 0) {
            val cell = ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum]
            if (cell !is Obstacle && cell !is Prey) {
                ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum] = Prey(
                    ocean = ocean,
                    coordinate = Coordinate(shuffledCells[i].row, shuffledCells[i].colum)
                )
                temp--
            }
            i++
        }

        temp = numberPredator
        while (temp > 0) {
            val cell = ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum]
            if (cell !is Obstacle && cell !is Prey && cell !is Predator) {
                ocean.ocean!![shuffledCells[i].row][shuffledCells[i].colum] = Predator(
                    ocean = ocean,
                    coordinate = Coordinate(shuffledCells[i].row, shuffledCells[i].colum)
                )
                temp--
            }
            i++
        }
        repository.oceans = ocean
        return ocean
    }

    fun output() =
        getOcean().ocean!!
            .joinToString("\n") {
                it.joinToString("") { call -> call.getDefaultImage() }
            } + "\n"

    fun iteration(index: Long) {
        val ocean = getOcean().ocean
        val processedCells = mutableSetOf<Cell>()

        ocean!!.forEach { calls ->
            calls.forEach { call ->
                if (call !in processedCells) {
                    call.process()
                    processedCells.add(call)
                }
            }
        }
    }

    fun reset() {
        repository.oceans = createOcean()
    }
}
