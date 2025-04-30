package `2024`.`21`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import kotlin.math.pow

val inputParser = InputParser("2024/21/input.txt")
val numpad = "789456123#0A".toList().toGrid(3)
val keypad = "#^A<v>".toList().toGrid(3)

val dMap: Map<Direction, Char> = mapOf(
    Direction.Up to '^',
    Direction.Down to 'v',
    Direction.Left to  '<',
    Direction.Right to '>'
)
val heuristic = mapOf(
    '<' to 1,
    'v' to 2,
    '>' to 4,
    '^' to 3,
)



fun partOne() = countByDepth(3)
fun partTwo() = countByDepth(26)

fun String.steps(): List<String> {
    val result = mutableListOf<String>()
    var index = 2
    while (index <= this.length) {
        result.add(this.substring(index - 2..<index))
        index++
    }
    return result
}

fun gridRoutes(grid: Grid<Char>): MutableMap<String, Set<String>> {
    val routes: MutableMap<String, Set<String>> = mutableMapOf()
    val keySet = grid.toSet().filter { it != '#' }
    keySet.forEach { outer ->
        keySet.forEach { inner ->
            val paths = mutableSetOf<String>()
            allPaths(paths, outer, inner, grid, "", mutableSetOf(outer))
            routes["$outer$inner"] = paths.sortedBy { it.length }.toSet()
        }
    }
    return routes
}

private fun optimalRoute(routes: Set<String>): String {
    return routes.map {
        var dC = 0
        var sum = 0L
        it.forEachIndexed { index, c ->
            sum += heuristic[c]!! * 10.0.pow(it.length - index).toLong()
            if (index != 0 && c != it[index - 1]) {
                dC++
            }
        }
        Triple(it, dC, sum)
    }.sortedWith(compareBy({it.second},{it.third})).first().first
}

private fun allPaths(
    paths: MutableSet<String>,
    start: Char,
    target: Char,
    grid: Grid<Char>,
    path: String,
    visited: MutableSet<Char>,
) {
    if (start == target) {
        paths.add(path)
    } else {
        val index = grid.indexOf(start)
        Direction.cardinalDirections()
            .mapNotNull {
                val nextC = grid.indexByDirection(index, it)
                val dirC = dMap[it]
                nextC?.let {
                    grid[it] to dirC
                }
            }
            .filter { it.first != '#' && it.first !in visited }
            .forEach { (next, dirC) ->
                visited.add(next)
                allPaths(paths, next, target, grid, path + dirC, visited)
                visited.remove(next)
            }
    }
}

fun countByDepth(depth: Int): Long {
    val optimal = (gridRoutes(keypad) + gridRoutes(numpad)).map {
        it.key to optimalRoute(it.value)
    }.toMap()
    return inputParser.lines().sumOf {
        val sC = "A$it".steps().associateWith { 1L }
        val sum = calculateCounts(0, depth, sC, optimal)
        sum * it.dropLast(1).toLong()
    }
}

fun calculateCounts(
    depth: Int,
    maxDepth: Int,
    counts: Map<String, Long>,
    optimal: Map<String, String>,
): Long {
    return if (depth == maxDepth) {
        counts.values.sum()
    } else {
        val subCounts = mutableMapOf<String, Long>()
        counts.forEach { entry ->
            "A${optimal[entry.key]}A".steps().forEach {
                subCounts.putIfAbsent(it, 0L)
                subCounts[it] = subCounts[it]!! + entry.value
            }
        }
        calculateCounts(depth + 1, maxDepth, subCounts, optimal)
    }
}

fun main() {
    
println(partOne())
    println(partTwo())
}
