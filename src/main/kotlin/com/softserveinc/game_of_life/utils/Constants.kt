package com.softserveinc.game_of_life.utils

object Constants {
    private val defaultTimeToReproduce = 7..10
    private val defaultTimeToFeed = 7..10
    fun randomTimeToReproduce() = defaultTimeToReproduce.random()
    fun randomDefaultTimeToFeed() = defaultTimeToFeed.random()
}