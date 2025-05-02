package `2017`.`01`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/01/input.txt")

private fun partOne(): Int {
    val input = inputParser.line()
    return input.matchedSums(1)
}

private fun String.matchedSums(offset: Int): Int {
    return this
        .withIndex()
        .filter { it.value == this[(it.index + offset) % this.length] }
        .sumOf { it.value.digitToInt() }
}

private fun partTwo(): Int {
    val input = inputParser.line()
    return input.matchedSums(input.length / 2)
}

private fun main() {
    println(partOne())
    println(partTwo())
}
