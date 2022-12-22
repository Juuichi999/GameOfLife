package com.softserveinc.game_of_life.models

abstract class CellBehavior(
    ocean: Ocean,
    coordinate: Coordinate,
) : Cell(ocean, coordinate) {
    companion object {
        val neighbors = listOf(
            Coordinate(-1, 0),
            Coordinate(0, -1),
            Coordinate(0, 1),
            Coordinate(0, 0),
            Coordinate(1, 0)
        )
    }

    protected fun changeCoordinate(coordinate: Coordinate) {
        val stabilizationCoordinate = stabilizationCoordinate(coordinate)
        ocean.ocean!![this.coordinate.row][this.coordinate.colum] = Empty(ocean, stabilizationCoordinate)
        ocean.ocean!![stabilizationCoordinate.row][stabilizationCoordinate.colum] = this
        this.coordinate = stabilizationCoordinate
    }

    fun moveRandom() {
        val emptyCell = neighbors.shuffled()
            .map { stabilizationCoordinate(it + this.coordinate) }
            .firstOrNull { ocean.ocean!![it.row][it.colum] is Empty }

        if (emptyCell != null) {
            changeCoordinate(emptyCell)
        }
    }

    fun reproduce() {
        val emptyCell = neighbors.shuffled()
            .map { stabilizationCoordinate(it + this.coordinate) }
            .firstOrNull { ocean.ocean!![it.row][it.colum] is Empty }

        emptyCell?.let {
            val newCell = this.createNewEntity(it)
            ocean.ocean!![it.row][it.colum] = newCell
        }
    }

    fun die() {
        ocean.ocean!![this.coordinate.row][this.coordinate.colum] = Empty(ocean, this.coordinate)
    }

    protected fun stabilizationCoordinate(coordinate: Coordinate): Coordinate {
        val row = (coordinate.row + ocean.ocean!!.size) % ocean.ocean!!.size
        val colum = (coordinate.colum + ocean.ocean!![0].size) % ocean.ocean!![0].size
        return Coordinate(row, colum)
    }
}
