package `2015`.`03`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2015/03/input.txt")

fun partOne(): Int {
    val visited = mutableSetOf(0 to 0)
    inputParser.line().traverseGrid(visited)
    return visited.size
}

private fun String.traverseGrid(visited: MutableSet<Pair<Int, Int>>) {
    this.fold(0 to 0) { acc, i ->
        val result = when (i) {
            '^' -> acc.first to acc.second + 1
            '>' -> acc.first + 1 to acc.second
            '<' -> acc.first - 1 to acc.second
            'v' -> acc.first to acc.second - 1
            else -> acc
        }
        visited.add(result)
        result
    }
}

fun partTwo(): Int {
    val santaPath = inputParser.line().filterIndexed { index, _ -> index % 2 == 0 }
    val roboPath = inputParser.line().filterIndexed { index, _ -> index % 2 == 1 }
    val visited = mutableSetOf(0 to 0)
    santaPath.traverseGrid(visited)
    roboPath.traverseGrid(visited)
    return visited.size
}

fun main() {
    println(partOne())
    println(partTwo())
}
