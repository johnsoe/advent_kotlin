package util.grid

sealed class Direction {
    object Up : Direction()
    object Down : Direction()
    object Left : Direction()
    object Right : Direction()

    object UpRight : Direction()
    object DownRight : Direction()
    object UpLeft : Direction()
    object DownLeft : Direction()

    fun turnLeft(): Direction {
        return when(this) {
            is Up -> Left
            is Down -> Right
            is Left -> Down
            is Right -> Up
            else -> Up
        }
    }

    fun turnRight(): Direction {
        return when(this) {
            is Up -> Right
            is Down -> Left
            is Left -> Up
            is Right -> Down
            else -> Up
        }
    }

    companion object {
        fun cardinalDirections(): List<Direction> = listOf(Up, Down, Left, Right)
        fun allDirections(): List<Direction> = cardinalDirections().toMutableList().apply {
            addAll(listOf(UpRight, DownRight, UpLeft, DownLeft))
        }
    }
}

