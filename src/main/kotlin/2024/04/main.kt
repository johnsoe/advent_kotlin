package `2024`.`04`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid

val inputParser = InputParser("2024/04/input.txt")



fun partOne(): Int {
    val xmasGrid = getXmasGrid()
    return xmasGrid.withIndex().sumOf {
        Direction.allDirections().count { dir ->
            listOfNotNull(
                it.index,
                xmasGrid.indexByDirection(it.index, dir),
                xmasGrid.indexByDirection(it.index, dir, 2),
                xmasGrid.indexByDirection(it.index, dir, 3)
            ).joinToString(separator = "") {
                xmasGrid[it].toString()
            } == "XMAS"
        }
    }
}

fun getXmasGrid(): Grid<Char> {
    return inputParser.lines()
        .map { it.toCharArray().toList() }
        .toGrid()
}

fun partTwo(): Int {
    val xmasGrid = getXmasGrid()
    return xmasGrid.withIndex().sumOf {
        Direction.diagonalDirections().count { dir ->
            val index = it.index
            val masList = listOf(
                index,
                xmasGrid.indexByDirection(index, dir),
                xmasGrid.indexByDirection(index, dir, 2),
            )
            val result: String = (masList + dir.adjacentDirsForDiagonal().map {
                xmasGrid.indexByDirection(index, it, 2)
            }).filterNotNull().joinToString(separator = "") {
                xmasGrid[it].toString()
            }
            result == "MASSM" || result == "MASMS"
        }
    } / 2
}

fun main() {
    println(partOne())
    println(partTwo())
}
