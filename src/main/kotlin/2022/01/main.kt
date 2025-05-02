package `2022`.`01`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2022/01/input.txt")

private fun partOne(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split(" ") }
        .map { it.sumOf { num -> num.toInt() } }
        .max()
}

private fun partTwo(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split(" ") }
        .map { it.sumOf { num -> num.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
