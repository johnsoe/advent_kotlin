import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.math.Vector2
import java.util.LinkedList

val inputParser = InputParser("2024/12/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val processedRegions = mutableMapOf<Char, MutableSet<Int>>()
    val grid = inputParser.charGrid()
    var price = 0
    grid.forEachIndexed { index, id ->
        processedRegions.putIfAbsent(id, mutableSetOf())
        if (processedRegions[id]?.contains(index) == false) {
            val toVisit = mutableSetOf<Int>()
            price += regionPrice(toVisit, id, index, grid)
            processedRegions[id]?.addAll(toVisit)
        }
    }
    return price
}

private fun regionPrice(visited: MutableSet<Int>, search: Char, start: Int, grid: Grid<Char>): Int {
    var borderCount = 0
    val queue = LinkedList<Int>()
    queue.add(start)
    visited.add(start)
    while (queue.isNotEmpty()) {
        val next = queue.removeFirst()
        val neighbors = grid.getAdjacentNeighbors(next)
            .filter { it.value == search }
        borderCount += (4 - neighbors.size)
        neighbors.filter { !visited.contains(it.index) }
            .apply {
                val nextIndices = this.map { it.index }
                queue.addAll(nextIndices)
                visited.addAll(nextIndices)
            }
    }
    return borderCount * visited.size
}

fun partTwo(): Int {
    val processedIndices = mutableSetOf<Int>()
    val grid = inputParser.charGrid()
    var price = 0
    grid.forEachIndexed { index, id ->
        if (!processedIndices.contains(index)) {
            val toVisit = mutableSetOf<Int>()
            regionPrice(toVisit, id, index, grid)
            price += actualPrice(toVisit, grid)
            processedIndices.addAll(toVisit)
        }
    }
    return price
}

private fun actualPrice(region: Set<Int>, grid: Grid<Char>): Int {
    val term = grid[region.first()]
    val vecs = region.map { grid.getVector(it) }
    return Direction.cardinalDirections().sumOf { dir ->
        analyzeEdges(vecs, grid, dir, term)
    } * region.size
}

private fun analyzeEdges(
    vecs: List<Vector2>,
    grid: Grid<Char>,
    dir: Direction,
    term: Char
): Int {
    val isXDir = dir.isXDirection()
    val xGroups = vecs.groupBy {
        if (isXDir) it.x else it.y
    }
    var total = 0
    xGroups.forEach {
        val sorted = it.value.sortedBy {
            if (isXDir) it.y else it.x
        }
        var edgeCount = 0
        var activeEdge = false
        sorted.forEachIndexed { i, vec ->
            val index = grid.indexByCoords(vec.x, vec.y)!!
            val above = grid.indexByDirection(index, dir)
            if (i == 0 || (isXDir && sorted[i-1].y != vec.y-1) ||
                (!isXDir && sorted[i - 1].x != vec.x - 1)) activeEdge = false
            if (above == null || grid[above] != term) {
                if (!activeEdge) {
                    activeEdge = true
                    edgeCount++
                }
            } else {
                activeEdge = false
            }
        }
        total += edgeCount
    }
    return total
}

private fun Direction.isXDirection(): Boolean {
    return when(this) {
        Direction.Right, Direction.Left -> true
        else -> false
    }
}
