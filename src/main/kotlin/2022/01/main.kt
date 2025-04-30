package `2022`.`01`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2022/01/input.txt")

fun partOne(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split(" ") }
        .map { it.sumOf { num -> num.toInt() } }
        .max()
}

fun partTwo(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split(" ") }
        .map { it.sumOf { num -> num.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()
}

fun main() {
    println(partOne())
    println(partTwo())
}
