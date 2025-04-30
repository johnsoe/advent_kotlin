package `2017`.`01`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2017/01/input.txt")

fun partOne(): Int {
    val input = inputParser.line()
    return input.matchedSums(1)
}

private fun String.matchedSums(offset: Int): Int {
    return this
        .withIndex()
        .filter { it.value == this[(it.index + offset) % this.length] }
        .sumOf { it.value.digitToInt() }
}

fun partTwo(): Int {
    val input = inputParser.line()
    return input.matchedSums(input.length / 2)
}

fun main() {
    println(partOne())
    println(partTwo())
}
