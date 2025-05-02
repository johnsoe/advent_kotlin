package `2017`.`11`

import util.InputParser
import util.math.Vector3
import util.string.toHexDirection
import kotlin.Int
import kotlin.math.abs
import kotlin.math.max

private val inputParser = InputParser("2017/11/input.txt")

private fun partOne(): Int {
    return inputParser.line()
        .split(",")
        .mapNotNull { it.toHexDirection()?.toVec() }
        .reduce { acc, vector3 -> acc + vector3 }
        .hexGridDistance(Vector3(0, 0, 0))
}

private fun partTwo(): Int {
    val start = Vector3(0, 0, 0)
    var maxDistance = 0
    inputParser.line()
        .split(",")
        .mapNotNull { it.toHexDirection()?.toVec() }
        .reduce { acc, vector3 ->
            val result = acc + vector3
            maxDistance = max(maxDistance, result.hexGridDistance(start))
            result
        }
    return maxDistance
}

fun Vector3.hexGridDistance(other: Vector3): Int {
    return (abs(other.q - q) + abs(other.r - r) + abs(other.s - s)) / 2
}

private fun main() {
    println(partOne())
    println(partTwo())
}
