package `2018`.`06`

import kotlin.Int
import util.InputParser
import util.math.Vector2
import util.math.manhattanDistance
import kotlin.collections.first
import kotlin.compareTo
import kotlin.ranges.rangeTo
import kotlin.text.lines

private val inputParser: InputParser = InputParser("2018/06/input.txt")

/**
 * --- Day 6: Chronal Coordinates ---
 *
 * Completed with the help of Github Copilot (2 Attempts, Needed context of Vector2)
 */
private fun partOne(): Int {
    val coordinates = inputParser.lines().map { line ->
        val (x, y) = line.split(", ").map { it.toInt() }
        Vector2(x, y)
    }

    val minX = coordinates.minOf { it.x }
    val maxX = coordinates.maxOf { it.x }
    val minY = coordinates.minOf { it.y }
    val maxY = coordinates.maxOf { it.y }

    val areaCounts = mutableMapOf<Vector2, Int>()
    val infiniteAreas = mutableSetOf<Vector2>()

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val distances = coordinates.map { it to it.manhattanDistance(Vector2(x, y)) }
            val closest = distances.groupBy { it.second }.minByOrNull { it.key }?.value

            if (closest != null && closest.size == 1) {
                val closestCoord = closest.first().first
                areaCounts[closestCoord] = areaCounts.getOrDefault(closestCoord, 0) + 1

                if (x == minX || x == maxX || y == minY || y == maxY) {
                    infiniteAreas.add(closestCoord)
                }
            }
        }
    }

    return areaCounts.filterKeys { it !in infiniteAreas }.maxOf { it.value }
}

private fun partTwo(): Int {
    val coordinates = inputParser.lines().map { line ->
        val (x, y) = line.split(", ").map { it.toInt() }
        Vector2(x, y)
    }

    val minX = coordinates.minOf { it.x }
    val maxX = coordinates.maxOf { it.x }
    val minY = coordinates.minOf { it.y }
    val maxY = coordinates.maxOf { it.y }

    val threshold = 10000
    var regionSize = 0

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val totalDistance = coordinates.sumOf { it.manhattanDistance(Vector2(x, y)) }
            if (totalDistance < threshold) {
                regionSize++
            }
        }
    }

    return regionSize
}

private fun main() {
    println(partOne())
    println(partTwo())
}
