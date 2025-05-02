package `2024`.`20`

import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.list.toCharGrid
import java.util.*
import kotlin.Int

private val inputParser = InputParser("2024/20/input.txt")

private fun partOne(): Int {
    val grid = inputParser.lines().toCharGrid()
    val cheats = findCheats(grid)
    val sDistance = bfsSteps(grid, 'S')
    return cheats.count { sDistance[it.second]!! - sDistance[it.first]!! > 0 }
}

private fun findCheats(grid: Grid<Char>): List<Pair<Int, Int>> {
    val map = grid.withIndex()
        .filter { it.value == '.' || it.value == 'S' }
        .associate {
            val validDirections = Direction.cardinalDirections().mapNotNull { dir ->
                val nextIndex = grid.indexByDirection(it.index, dir, 1)
                val secondIndex = grid.indexByDirection(it.index, dir, 2)
                if (nextIndex != null && secondIndex != null && grid[nextIndex] == '#' && grid[secondIndex].isValidCheatSpot()) {
                    secondIndex
                } else {
                    null
                }
            }
            it.index to validDirections
        }

    val result = mutableListOf<Pair<Int, Int>>()
    map.keys.forEach { key ->
        map[key]?.forEach {
            result.add(key to it)
        }
    }
    return result
}

private fun Char.isValidCheatSpot() = this == '.' || this == 'E'

private fun bfsSteps(grid: Grid<Char>, start: Char): Map<Int, Int> {
    val stepCounts = mutableMapOf<Int, Int>()
    val startIndex = grid.indexOf(start)
    val queue = LinkedList<Pair<Int, Int>>()
    val visited = mutableSetOf(startIndex)
    queue.add(startIndex to 0)
    while (queue.isNotEmpty()) {
        val (index, step) = queue.removeFirst()
        stepCounts[index] = step
        Direction.cardinalDirections()
            .mapNotNull { dir -> grid.indexByDirection(index, dir) }
            .filter { grid[it].isValidCheatSpot() && it !in visited }
            .forEach {
                visited.add(it)
                queue.add(it to step + 1)
            }
    }
    return stepCounts
}

private fun partTwo(): Int {
    val grid = inputParser.lines().toCharGrid()
    val sDistance = bfsSteps(grid, 'S')
    val allCheats = grid.withIndex()
        .filter { it.value == 'S' || it.value == '.' }
        .map { it.index to allCheats(it.index, grid) }

    return allCheats.map { (s, cs) ->
        cs.map { sDistance[it.first]!! - sDistance[s]!! - it.second }
            .filter { it >= 100 }
    }.flatten().size
}

private fun allCheats(startIndex: Int, grid: Grid<Char>): List<Pair<Int, Int>> {
    return grid.withIndex().filter { it.value == 'E' || it.value == '.' }
        .mapNotNull {
            val distance = grid.manhattanDistance(startIndex, it.index)
            if (distance <= 20) it.index to distance else null
        }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
