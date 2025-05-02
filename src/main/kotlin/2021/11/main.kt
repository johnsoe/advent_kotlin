package `2021`.`11`

import util.InputParser
import util.grid.Grid
import util.grid.toGrid
import java.util.*
import java.util.ArrayDeque
import kotlin.Int

private val inputParser = InputParser("2021/11/input.txt")

private fun partOne(): Int {
    val width = inputParser.lines().size
    var octos = inputParser.chunkAndJoin("").first()
        .map { Character.getNumericValue(it) }
        .toGrid(width)
    var flashSum = 0
    repeat(100) {
        octos = octos.map { it + 1 }.toGrid(width)
        val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
        flashSum += flash(octos, ArrayDeque<Int>().apply { addAll(indices) })
    }
    println(octos)
    return flashSum
}

fun flash(data: Grid<Int>, indexToCheck: Queue<Int>): Int {
    var flashCount = 0
    while (!indexToCheck.isEmpty()) {
        val currentIndex = indexToCheck.remove()
        val neighbors = data.getAllNeighbors(currentIndex)
        val currentVal = data[currentIndex]
        if (currentVal > 9) {
            data[currentIndex] = 0
            flashCount++
            neighbors.forEach {
                if (it.value != 0) {
                    data[it.index] = it.value + 1
                }
            }
            indexToCheck.addAll(neighbors.map { it.index })
        }
    }
    return flashCount
}

private fun partTwo(): Int {
    val width = inputParser.lines().size
    var octos = inputParser.chunkAndJoin("").first()
        .map { Character.getNumericValue(it) }
        .toGrid(width)
    var flashSum = 0
    var count = 0
    while (flashSum != 100) {
        octos = octos.map { it + 1 }.toGrid(width)
        val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
        flashSum = flash(octos, ArrayDeque<Int>().apply { addAll(indices) })
        count++
    }
    return count
}

private fun main() {
    println(partOne())
    println(partTwo())
}
