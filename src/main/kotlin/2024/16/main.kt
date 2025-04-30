package `2024`.`16`

import util.InputParser
import util.grid.Direction
import java.util.PriorityQueue
import kotlin.Int

val inputParser = InputParser("2024/16/input.txt")
val grid = inputParser.charGrid()
val gEnd = grid.indexOf('E')
val gStart = grid.indexOf('S')

fun partOne(): Int {
    val scores = generateCellScores()
    return scores[gEnd] ?: 0
}

private fun generateCellScores(): Map<Int, Int> {
    val visited = mutableMapOf(gStart to 0)
    val queue = PriorityQueue<Cell> { c1, c2 -> c1.score - c2.score }
    queue.add(Cell(0, Direction.Right, gStart))
    while (queue.isNotEmpty()) {
        val next = queue.remove()
        Direction.cardinalDirections().forEach { nextDir ->
            grid.indexByDirection(next.index, nextDir)?.let { neighbor ->
                if (grid[neighbor] != '#' && !visited.contains(neighbor)) {
                    val delta = 1 + if (nextDir == next.dir) 0 else 1000
                    visited[neighbor] = next.score + delta
                    queue.add(Cell(next.score + delta, nextDir, neighbor))
                }
            }
        }
    }
    return visited
}

private data class Cell(
    val score: Int,
    val dir: Direction,
    val index: Int,
)

private fun dfsGridHelper(
    visited: MutableSet<Int>,
    scores: Map<Int, Int>,
    paths: MutableSet<Int>,
    current: Cell,
) {
    val index = current.index
    if (index == gEnd) {
        if (current.score == scores[index]) {
            paths.addAll(visited)
        }
    } else {
        Direction.cardinalDirections().forEach { nextDir ->
            val n = grid.indexByDirection(index, nextDir)!!
            val delta = 1 + if (nextDir == current.dir) 0 else 1000
            val nextScore = current.score + delta
            if (grid[n] != '#' && !visited.contains(n) && nextScore - 1000 <= scores[n]!!) {
                val cell = Cell(nextScore, nextDir, n)
                visited.add(n)
                dfsGridHelper(visited, scores, paths, cell)
                visited.remove(n)
            }
        }
    }
}

fun partTwo(): Int {
    val scores = generateCellScores()
    val path = mutableSetOf(gStart)
    dfsGridHelper(mutableSetOf(), scores, path, Cell(0, Direction.Right, gStart))
    return path.size
}

fun main() {
    println(partOne())
    println(partTwo())
}
