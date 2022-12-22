package com.softserveinc.game_of_life.models

data class Coordinate(
    val row: Int,
    val colum: Int
) {
    operator fun plus(other: Coordinate) = Coordinate(row + other.row, colum + other.colum)
}
