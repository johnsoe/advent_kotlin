package `2022`.`03`

import util.InputParser
import util.string.splitInHalf
import kotlin.Int

private val inputParser = InputParser("2022/03/input.txt")

private fun partOne(): Int {
    return inputParser.lines()
        .asSequence()
        .map { it.splitInHalf() }
        .map { it.first.toCharArray().intersect(it.second.toCharArray().toSet()) }
        .map { it.first() }
        .sumOf { it.mapToPriority() }
}

fun Char.mapToPriority(): Int {
    return if (this.isLowerCase()) {
        this.code - 96
    } else {
        this.code - 38
    }
}

private fun partTwo(): Int {
    return inputParser.lines()
        .chunked(3)
        .map {
            it.map { ruck -> ruck.toCharArray() }
                .reduce { acc, chars ->
                    acc.intersect(chars.toSet()).toCharArray()
                }
        }
        .map { it.first() }
        .sumOf { it.mapToPriority() }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
