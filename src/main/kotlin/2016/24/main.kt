package `2016`.`24`

import util.InputParser
import util.grid.Grid
import util.grid.toGrid
import java.util.LinkedList
import kotlin.Int
import kotlin.math.min

private val inputParser = InputParser("2016/24/input.txt")

private fun partOne(): Int {
    val grid = createGrid()
    val pointsOfInterest = createPointsOfInterest(grid)
    val distances = populateDistanceMatrix(grid, pointsOfInterest)
    return calculateShortestDistance(
        current = 0,
        distances = distances,
        visited = mutableSetOf(0),
        steps = 0,
        returnToStart = false,
    )
}

fun createGrid(): Grid<Char> {
    return inputParser.lines()
        .map { it.toCharArray().toList() }
        .toGrid()
}

fun createPointsOfInterest(grid: Grid<Char>): List<Pair<Char, Int>> {
    return grid.mapIndexed { index, c -> c to index }
        .filter { (c, _) -> c != '#' && c != '.' }
}

private fun populateDistanceMatrix(grid: Grid<Char>, pointsOfInterest: List<Pair<Char, Int>>): Array<IntArray> {
    val distances = Array(pointsOfInterest.size) {
        IntArray(pointsOfInterest.size) { 0 }
    }

    pointsOfInterest.forEach { (c, index) ->
        val visited = mutableSetOf(index)
        val queue = LinkedList<Pair<Int, Int>>().apply { add(index to 0) }
        while (queue.isNotEmpty()) {
            val (next, distance) = queue.removeFirst()
            val gridVal = grid[next]
            if (gridVal.isDigit()) {
                distances[c.digitToInt()][gridVal.digitToInt()] = distance
            }
            grid.getAdjacentNeighbors(next)
                .filter { it.value != '#' && it.index !in visited }
                .map { it.index to distance + 1 }
                .apply {
                    queue.addAll(this)
                    visited.addAll(this.map { it.first })
                }
        }
    }
    return distances
}

fun calculateShortestDistance(
    current: Int,
    distances: Array<IntArray>,
    visited: MutableSet<Int>,
    steps: Int,
    returnToStart: Boolean,
): Int {
    return if (visited.size == distances.size) {
        if (returnToStart) {
            val returnTrip = distances[current][0]
            steps + returnTrip
        } else {
            steps
        }
    } else {
        var min = Integer.MAX_VALUE
        distances.forEachIndexed { index, _ ->
            if (index !in visited) {
                visited.add(index)
                val result = calculateShortestDistance(index, distances, visited, steps + distances[current][index], returnToStart)
                visited.remove(index)
                min = min(result, min)
            }
        }
        min
    }
}

private fun partTwo(): Int {
    val grid = createGrid()
    val pointsOfInterest = createPointsOfInterest(grid)
    val distances = populateDistanceMatrix(grid, pointsOfInterest)
    return calculateShortestDistance(
        current = 0,
        distances = distances,
        visited = mutableSetOf(0),
        steps = 0,
        returnToStart = true,
    )
}

private fun main() {
    println(partOne())
    println(partTwo())
}
