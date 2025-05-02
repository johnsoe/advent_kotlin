package `2015`.`18`

import util.InputParser
import util.grid.Grid
import util.grid.toGrid
import kotlin.Int

private val inputParser = InputParser("2015/18/input.txt")

private fun partOne(): Int {
    val grid = createGrid()
    repeat(100) {
        grid.animate()
    }
    return grid.count { it }
}

fun Grid<Boolean>.animate() {
    val next = mutableListOf<Boolean>()
    this.forEachIndexed { index, isOn ->
        val onCount = this.getAllNeighbors(index).count { it.value }
        val onNext = onCount == 3 || (onCount == 2 && isOn)
        next.add(index, onNext)
    }
    this.forEachIndexed { index, _ ->
        this[index] = next[index]
    }
}

fun createGrid(): Grid<Boolean> {
    return inputParser.lines().map {
        it.toCharArray().map { cell ->
            when (cell) {
                '.' -> false
                else -> true
            }
        }
    }.toGrid()
}

private fun partTwo(): Int {
    val grid = createGrid()
    grid.setCorners(true)
    repeat(100) {
        grid.animate()
        grid.setCorners(true)
    }
    return grid.count { it }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
