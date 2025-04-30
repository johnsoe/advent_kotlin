package `2015`.`06`

import util.InputParser
import util.grid.Grid
import util.grid.toGrid
import java.awt.Point
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.math.max

val inputParser = InputParser("2015/06/input.txt")
val regex = """(.*) (\d+),(\d+) through (\d+),(\d+)""".toRegex()

fun partOne(): Int {
    val grid = createGrid(false)
    getCommands().forEach {
        (it.start.y..it.end.y).forEach { y ->
            (it.start.x..it.end.x).forEach { x ->
                val index = grid.indexByCoords(x, y)!!
                grid[index] = when (it.action) {
                    LightAction.OFF -> false
                    LightAction.ON -> true
                    LightAction.TOGGLE -> !grid[index]
                }
            }
        }
    }
    return grid.count { it }
}

private inline fun <reified T> createGrid(init: T): Grid<T> {
    return Array(1000) {
        Array(1000) { init }
    }.toGrid()
}

private fun getCommands(): List<LightCommand> {
    return inputParser.lines()
        .map { regex.find(it)?.destructured?.toList() ?: emptyList() }
        .filter { it.isNotEmpty() }
        .map {
            val action = when (it[0]) {
                "turn off" -> LightAction.OFF
                "turn on" -> LightAction.ON
                "toggle" -> LightAction.TOGGLE
                else -> throw UnsupportedOperationException()
            }
            val nums = it.drop(1).map { it.toInt() }
            LightCommand(
                action,
                Point(nums[0], nums[1]),
                Point(nums[2], nums[3]),
            )
        }
}

fun partTwo(): Int {
    val grid = createGrid(0)
    getCommands().forEach {
        (it.start.y..it.end.y).forEach { y ->
            (it.start.x..it.end.x).forEach { x ->
                val index = grid.indexByCoords(x, y)!!
                grid[index] = when (it.action) {
                    LightAction.OFF -> max(grid[index] - 1, 0)
                    LightAction.ON -> grid[index] + 1
                    LightAction.TOGGLE -> grid[index] + 2
                }
            }
        }
    }
    return grid.sum()
}

fun main() {
    println(partOne())
    println(partTwo())
}
