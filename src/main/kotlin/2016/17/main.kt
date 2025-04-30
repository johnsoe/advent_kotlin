package `2016`.`17`

import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import java.security.MessageDigest
import java.util.LinkedList
import kotlin.Int
import kotlin.math.max

val inputParser = InputParser("2016/17/input.txt")
val md = MessageDigest.getInstance("MD5")
val coordinateDirections = listOf(Direction.Up, Direction.Down, Direction.Left, Direction.Right)

fun partOne(): String {
    val input = inputParser.line()
    val grid = List(size = 16) { 1 }.toGrid(4)
    val positions = LinkedList<Pair<Int, String>>().apply {
        add(0 to input)
    }
    while (positions.isNotEmpty() && positions.first().first != 15) {
        val (index, path) = positions.remove()
        val directions = path.getDirections()
        positions.addAll(getNextSteps(directions, grid, path, index))
    }
    val result = positions.first().second
    return result.drop(input.length)
}

private fun getNextSteps(
    directions: String,
    grid: Grid<Int>,
    path: String,
    index: Int,
): List<Pair<Int, String>> {
    return directions.mapIndexed { i, c ->
        coordinateDirections[i] to c
    }.filter { (_, c) ->
        c.isDoorOpen()
    }.mapNotNull { (dir, _) ->
        val neighbor = grid.indexByDirection(index, dir)
        neighbor?.let {
            it to "$path${dir.getDirChar()}"
        }
    }
}

fun partTwo(): Int {
    val input = inputParser.line()
    val grid = List(size = 16) { 1 }.toGrid(4)
    val positions = LinkedList<Pair<Int, String>>().apply {
        add(0 to input)
    }
    var longestSolution = 0
    while (positions.isNotEmpty()) {
        val (index, path) = positions.remove()
        if (index == 15) {
            longestSolution = max(longestSolution, path.length - input.length)
            continue
        }
        val directions = path.getDirections()
        positions.addAll(getNextSteps(directions, grid, path, index))
    }
    return longestSolution
}

private fun Char.isDoorOpen(): Boolean {
    return this in "bcdef"
}

private fun String.getDirections(): String {
    val result = md.digest(this.toByteArray())
    return result.joinToString("") { "%02x".format(it) }
        .take(4)
}

private fun Direction.getDirChar(): Char {
    return when (this) {
        Direction.Up -> 'U'
        Direction.Down -> 'D'
        Direction.Left -> 'L'
        Direction.Right -> 'R'
        else -> 'X'
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
