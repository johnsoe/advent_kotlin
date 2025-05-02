package `2021`.`01`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2021/01/input.txt")

private fun partOne(): Int {
    return increaseCount(inputParser.linesInt())
}

fun increaseCount(input: List<Int>): Int {
    return input.filterIndexed { index, i ->
        input[Integer.max(index - 1, 0)] < i
    }.size
}

private fun partTwo(): Int {
    val input = inputParser.linesInt()
    val summedInput = input.mapIndexed { index, i ->
        i + (input.getOrNull(index + 1) ?: 0) + (input.getOrNull(index + 2) ?: 0)
    }
    return increaseCount(summedInput)
}

private fun main() {
    println(partOne())
    println(partTwo())
}
