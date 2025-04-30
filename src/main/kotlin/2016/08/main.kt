package `2016`.`08`

import `2016.08`.DisplayCommand
import kotlin.Int
import util.InputParser
import util.grid.Grid
import util.grid.toGrid

val inputParser = InputParser("2016/08/input.txt")



fun partOne(): Int {
    val grid = Array(50 * 6) { false }.toList().toGrid(50)
    getInputCommands().forEach {
        when(it) {
            is DisplayCommand.ShiftCol -> grid.shiftCol(it.col, it.shift)
            is DisplayCommand.ShiftRow -> grid.shiftRow(it.row, it.shift)
            is DisplayCommand.DrawRect -> grid.setRect(it.width, it.height)
        }
    }
    return grid.count { it }
}

private fun getInputCommands(): List<DisplayCommand> {
    return inputParser.lines()
        .map {it.split(" ")}
        .map {
            when {
                it.first() == "rect" -> {
                    val dimens = it.last().split('x').map { it.toInt() }
                    DisplayCommand.DrawRect(dimens.first(), dimens.last())
                }
                else -> {
                    val shift = it.last().toInt()
                    val row = it[2].split("=").last().toInt()
                    if (it[1] == "row") {
                        DisplayCommand.ShiftRow(row, shift)
                    } else {
                        DisplayCommand.ShiftCol(row, shift)
                    }
                }
            }
        }
}

fun Grid<Boolean>.setRect(width: Int, height: Int) {
    for (x in 0 until width) {
        for (y in 0 until height) {
            val index = this.indexByCoords(x, y)!!
            this[index] = true
        }
    }
}

fun partTwo(): Int {
    val grid = Array(50 * 6) { false }.toList().toGrid(50)
    getInputCommands().forEach {
        when(it) {
            is DisplayCommand.ShiftCol -> grid.shiftCol(it.col, it.shift)
            is DisplayCommand.ShiftRow -> grid.shiftRow(it.row, it.shift)
            is DisplayCommand.DrawRect -> grid.setRect(it.width, it.height)
        }
    }
    grid.forEachIndexed { index, b ->
        if (index % grid.width == 0) {
            println()
        }
        if (b) print('#') else print('.')
    }
    println()
    return 0
}

fun main() {
    println(partOne())
    println(partTwo())
}
