package util

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
            is Direction.Up -> Direction.Left
            is Direction.Down -> Direction.Right
            is Direction.Left -> Direction.Down
            is Direction.Right -> Direction.Up
            else -> Direction.Up
        }
    }

    fun turnRight(): Direction {
        return when(this) {
            is Direction.Up -> Direction.Right
            is Direction.Down -> Direction.Left
            is Direction.Left -> Direction.Up
            is Direction.Right -> Direction.Down
            else -> Direction.Up
        }
    }

    companion object {
        fun cardinalDirections(): List<Direction> = listOf(Direction.Up, Direction.Down, Direction.Left, Direction.Right)
        fun allDirections(): List<Direction> = cardinalDirections().toMutableList().apply {
            addAll(listOf(Direction.UpRight, Direction.DownRight, Direction.UpLeft, Direction.DownLeft))
        }
    }
}

