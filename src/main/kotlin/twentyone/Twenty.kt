package twentyone

import InputParser
import LegacyInputParser
import util.Grid
import java.lang.IllegalStateException
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Twenty : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "twentyone/input/twenty.txt"

    public override fun partOne(): Int {
        val input = getInputByLine()
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

    private fun expandImage(grid: Grid<Char>, expansionChar: Char): Grid<Char> {

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

    public override fun partTwo(): Int {
        println("partTwo")
        return 0
    }
}

public fun main(): Unit {
    println(Twenty.partOne())
    println(Twenty.partTwo())
}
