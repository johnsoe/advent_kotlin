package `2023`.`13`

import kotlin.Int
import util.InputParser
import util.grid.Grid
import util.grid.toGrid

val inputParser = InputParser("2023/13/input.txt")



fun partOne(): Int {
    return createGrids().map { grid ->
        val row = (0 until grid.height - 1)
            .filter { getFullDiffCount(it, grid::getRow, grid.height) == 0 }
            .map { it + 1 }
            .firstOrNull()

        val col = (0 until grid.width - 1)
            .filter { getFullDiffCount(it, grid::getCol, grid.width) == 0 }
            .map { it + 1 }
            .firstOrNull()

        row to col
    }.sumOf { it.sum() }
}

fun createGrids(): List<Grid<Char>> {
    return inputParser.chunk()
        .map {
            var width = 0
            it.map {
                width = it.length
                it.toCharArray().toList()
            }
                .flatten()
                .toGrid(width)
        }
}

fun Pair<Int?, Int?>.sum(): Int {
    val (row, col) = this
    return when {
        row == null -> col!!
        col == null -> row * 100
        else -> 0
    }
}

fun List<Char>.getDiffCount(other: List<Char>): Int {
    return if (this == other) {
        0
    } else {
        this.filterIndexed { index, c ->
            c != other[index]
        }.size
    }
}

fun getFullDiffCount(
    index: Int,
    gridcb: (Int) -> List<Char>,
    upperBound: Int
): Int {
    var sum = 0
    var offset = 0
    while (index - offset >= 0 && index + offset + 1 < upperBound) {
        sum += gridcb(index - offset).getDiffCount(gridcb(index + offset + 1))
        offset++
    }
    return sum
}

fun partTwo(): Int {
    return createGrids().map { grid ->
        val row = (0 until grid.height - 1)
            .map { it + 1 to getFullDiffCount(it, grid::getRow, grid.height)  }
            .firstOrNull { it.second == 1 }?.first

        val col = (0 until grid.width - 1)
            .map { it + 1 to getFullDiffCount(it, grid::getCol, grid.width) }
            .firstOrNull { it.second == 1 }?.first

        row to col
    }.sumOf { it.sum() }
}

fun main() {
    println(partOne())
    println(partTwo())
}
