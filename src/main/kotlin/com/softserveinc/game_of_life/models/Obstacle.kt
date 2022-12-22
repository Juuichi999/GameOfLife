package com.softserveinc.game_of_life.models

class Obstacle(ocean: Ocean, coordinate: Coordinate) : Cell(ocean, coordinate) {
    override fun getDefaultImage() = "🪸"
    override fun process() {}
    override fun createNewEntity(coordinate: Coordinate) = Obstacle(ocean, coordinate)
}
