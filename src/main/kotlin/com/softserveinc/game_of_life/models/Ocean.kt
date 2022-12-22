package com.softserveinc.game_of_life.models

class Ocean(var ocean: Array<Array<Cell>>? = null) {

    fun toOceanEntity(): OceanEntity {
        var predator = 0
        var prey = 0
        val oceanForEntity = ocean!!
            .map {
                it.map { cell ->
                    when (cell) {
                        is Predator -> {
                            predator++
                            1
                        }

                        is Prey -> {
                            prey++
                            2
                        }

                        is Obstacle -> {
                            3
                        }

                        else -> {
                            0
                        }
                    }
                }
            }
        return OceanEntity(
            predator,
            prey,
            oceanForEntity
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ocean

        if (ocean != null) {
            if (other.ocean == null) return false
            if (!ocean.contentDeepEquals(other.ocean)) return false
        } else if (other.ocean != null) return false

        return true
    }

    override fun hashCode(): Int {
        return ocean?.contentDeepHashCode() ?: 0
    }
}
