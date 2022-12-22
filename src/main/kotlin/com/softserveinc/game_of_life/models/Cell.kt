package com.softserveinc.game_of_life.models

abstract class Cell(
    protected open val ocean: Ocean,
    protected var coordinate: Coordinate,
) {
    abstract fun getDefaultImage(): String
    abstract fun process()
    protected abstract fun createNewEntity(coordinate: Coordinate): Cell

}