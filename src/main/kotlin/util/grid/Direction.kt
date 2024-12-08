package util.grid

import util.math.Vector2

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

    fun opposite(): Direction {
        return when(this) {
            is Up -> Down
            is Down -> Up
            is Left -> Right
            is Right -> Left
            is UpLeft -> DownRight
            is UpRight -> DownLeft
            is DownLeft -> UpRight
            is DownRight -> UpLeft
        }
    }

    fun adjacentDirsForDiagonal(): List<Direction> {
        return when (this) {
            UpRight -> listOf(Up, Right)
            UpLeft -> listOf(Up, Left)
            DownRight -> listOf(Down, Right)
            DownLeft -> listOf(Down, Left)
            else -> emptyList()
        }
    }

    fun toVec(): Vector2 {
        return when(this) {
            is Up -> Vector2(0, -1)
            is Down -> Vector2(0, 1)
            is Left -> Vector2(-1, 0)
            is Right -> Vector2(1, 0)
            is UpLeft -> Vector2(-1, -1)
            is UpRight -> Vector2(1, -1)
            is DownLeft -> Vector2(-1, 1)
            is DownRight -> Vector2(1, 1)
        }
    }

    companion object {
        fun cardinalDirections(): List<Direction> = listOf(Up, Right, Down, Left)
        fun diagonalDirections() = listOf(UpRight, DownRight, UpLeft, DownLeft)
        fun allDirections(): List<Direction> = cardinalDirections() + diagonalDirections()

        fun directionMap(): Map<Char, Direction> = mapOf(
            'U' to Up,
            'D' to Down,
            'L' to Left,
            'R' to Right
        )
    }
}

