package `2021`.`20`

import util.InputParser
import util.grid.Grid
import java.lang.IllegalStateException
import kotlin.Int

val inputParser = InputParser("2021/20/input.txt")

fun partOne(): Int {
    val input = inputParser.lines()
    val enhancement = input.first()
    val image = input.drop(2).map {
        it.toCharArray().toList()
    }.flatten()
    var grid = Grid<Char>(input.last().length).apply { addAll(image) }
    repeat(50) {
        grid = expandImage(grid, if (it % 2 == 0) '.' else '#')
        val enhanceIndices = grid.mapIndexed { index, c ->
            val neighbors = grid.getAllNeighbors(index)
            when {
                neighbors.all { it.value == '#' } && neighbors.size < 8 -> 511
                neighbors.all { it.value == '.' } && neighbors.size < 8 -> 0
                else -> {
                    neighbors.toMutableSet()
                        .apply { add(IndexedValue(index, c)) }
                        .sortedBy { it.index }
                        .map {
                            when (it.value) {
                                '#' -> '1'
                                '.' -> '0'
                                else -> throw IllegalStateException()
                            }
                        }.joinToString("")
                        .toInt(2)
                }
            }
        }
        grid.forEachIndexed { index, c ->
            grid[index] = enhancement[enhanceIndices[index]]
        }
    }
    return grid.count { it == '#' }
}

fun expandImage(grid: Grid<Char>, expansionChar: Char): Grid<Char> {
    val newGrid = Grid<Char>(grid.width + 4)
    val emptyRow = CharArray(grid.width + 4) { expansionChar }.toList()
    newGrid.addAll(emptyRow)
    newGrid.addAll(emptyRow)
    grid.getGridAs2D().forEach {
        newGrid.add(expansionChar)
        newGrid.add(expansionChar)
        newGrid.addAll(it)
        newGrid.add(expansionChar)
        newGrid.add(expansionChar)
    }
    newGrid.addAll(emptyRow)
    newGrid.addAll(emptyRow)
    return newGrid
}

fun partTwo(): Int {
    return 0
}

fun main() {
    println(partOne())
    println(partTwo())
    println("partTwo")
}
