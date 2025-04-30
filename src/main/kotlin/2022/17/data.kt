package `2022`.`17`

import util.grid.Direction
import java.awt.Point

sealed class Rock(val coords: MutableSet<Point>) {
    object Line : Rock(
        mutableSetOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0)),
    )
    object Cross : Rock(
        mutableSetOf(Point(0, 0), Point(1, -1), Point(0, -1), Point(-1, -1), Point(0, -2)),
    )
    object Elbow : Rock(
        mutableSetOf(Point(0, 0), Point(0, -1), Point(0, -2), Point(-1, -2), Point(-2, -2)),
    )
    object Vert : Rock(
        mutableSetOf(Point(0, 0), Point(0, -1), Point(0, -2), Point(0, -3)),
    )
    object Square : Rock(
        mutableSetOf(Point(0, 0), Point(0, -1), Point(1, 0), Point(1, -1)),
    )

    fun Rock.shift(direction: Direction) {
        val shiftVector = when (direction) {
            Direction.Left -> Point(-1, 0)
            Direction.Right -> Point(1, 0)
            Direction.Down -> Point(0, -1)
            else -> Point(0, 0)
        }
        this.coords.forEach {
            it.x = shiftVector.x + it.x
            it.y = shiftVector.y + it.y
        }
    }

    fun Rock.setInitOffset(offset: Point) {
        this.coords.forEach {
            it.y = it.y + offset.y
            it.x = offset.x + it.x
        }
    }

    fun Rock.rightMost(): Int {
        return coords.maxOf { it.x }
    }

    fun Rock.leftMost(): Int {
        return coords.maxOf { it.y }
    }
}
