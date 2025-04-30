package `2024`.`10`

import util.InputParser
import util.grid.Grid
import kotlin.Int

val inputParser = InputParser("2024/10/input.txt")

fun partOne(): Int {
    val mountain = inputParser.intGrid()
    val trailheads = mountain.withIndex().filter { it.value == 0 }
    return trailheads.sumOf {
        val peaks = mutableSetOf<Int>()
        traverseMountain(mountain, it.value, it.index, peaks)
        peaks.size
    }
}

// Keep track of peaks visited as well as unique paths.
fun traverseMountain(
    mountain: Grid<Int>,
    current: Int,
    index: Int,
    peaks: MutableSet<Int>,
): Int {
    return if (current == 9) {
        peaks.add(index)
        1
    } else {
        mountain.getAdjacentNeighbors(index).filter {
            it.value == current + 1
        }.sumOf {
            traverseMountain(mountain, it.value, it.index, peaks)
        }
    }
}

fun partTwo(): Int {
    val mountain = inputParser.intGrid()
    val trailheads = mountain.withIndex().filter { it.value == 0 }
    return trailheads.sumOf {
        val peaks = mutableSetOf<Int>()
        traverseMountain(mountain, it.value, it.index, peaks)
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
