package `2024`.`18`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import java.util.LinkedList

val inputParser = InputParser("2024/18/input.txt")
private val gridSize = 71



fun partOne(): Int {
    val grid = Array(gridSize) { Array(gridSize) { true } }.toGrid()
    val bytes = inputParser.lines()
        .take(1024)
        .map {
            it.split(",")
                .map { it.toInt() }
        }
    bytes.forEach {
        grid[grid.indexByCoords(it.first(), it.last())!!] = false
    }
    return bfs(grid)
}

private fun bfs(grid: Grid<Boolean>): Int {
    val start = 0
    val target = gridSize * gridSize - 1

    val queue = LinkedList<Pair<Int, Int>>()
    val visited = mutableSetOf(start)
    queue.add(start to 0)
    while (queue.isNotEmpty()) {
        val (next, count) = queue.removeFirst()
        if (next == target) return count
        Direction.cardinalDirections().forEach {
            val n = grid.indexByDirection(next, it)
            if (n != null && n !in visited && grid[n]) {
                visited.add(n)
                queue.add(n to count + 1)
            }
        }
    }
    return -1
}

fun partTwo(): String {
    var count = 1024
    val grid = Array(gridSize) { Array(gridSize) { true } }.toGrid()
    val bytes = inputParser.lines()
        .map {
            it.split(",")
                .map { it.toInt() }
        }
    bytes.take(count).forEach {
        grid[grid.indexByCoords(it.first(), it.last())!!] = false
    }
    while(bfs(grid) != -1) {
        count++
        val byte = bytes[count]
        grid[grid.indexByCoords(byte.first(), byte.last())!!] = false
    }
    return bytes[count].toString()
}

fun main() {
    println(partOne())
    println(partTwo())
}
