package `2016`.`13`

import util.InputParser
import util.grid.Grid
import util.grid.toGrid
import java.util.*
import kotlin.Int
import kotlin.math.pow

private val inputParser = InputParser("2016/13/input.txt")
val masks = IntRange(0, 31).map { 2.0.pow(it).toInt() }
val width = 75

private fun partOne(): Int {
    val grid = generateGrid()
    val result = traverse(grid)

    return result[grid.indexByCoords(31, 39)] ?: 0
}

fun generateGrid(): Grid<Boolean> {
    val favoriteNum = inputParser.line().toInt()
    return Array(width) { row ->
        Array(width) { col ->
            isWall(col, row, favoriteNum)
        }
    }.toGrid()
}

fun isWall(x: Int, y: Int, seed: Int): Boolean {
    val sum = (x * x + 3 * x + 2 * x * y + y + y * y) + seed
    return masks.count {
        (it and sum) != 0
    } % 2 != 0
}

// bfs of grid to populate step count to position
fun traverse(grid: Grid<Boolean>): Map<Int, Int> {
    val start = grid.indexByCoords(1, 1)!!
    val visited = mutableMapOf<Int, Int>()
    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(start to 0)
    while (queue.isNotEmpty()) {
        val (index, count) = queue.remove()
        visited[index] = count
        val neighbors = grid.getAdjacentNeighbors(index)
            .filter {
                it.value && !visited.contains(it.index)
            }
            .map {
                it.index to count + 1
            }
        queue.addAll(neighbors)
    }
    return visited
}

private fun partTwo(): Int {
    val grid = generateGrid()
    val result = traverse(grid)

    return result.values.filter { it <= 50 }.size
}

private fun main() {
    println(partOne())
    println(partTwo())
}
