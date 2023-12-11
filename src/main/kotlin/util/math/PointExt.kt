package util.math

import java.awt.Point
import kotlin.math.abs

fun Point.manhattanDistance(other: Point): Long {
    val xDelta = abs(this.x - other.x).toLong()
    val yDelta = abs(this.y - other.y).toLong()
    return xDelta + yDelta
}