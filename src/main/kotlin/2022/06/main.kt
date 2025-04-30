package `2022`.`06`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2022/06/input.txt")

fun partOne(): Int {
    val input = inputParser.lines().first()
    input.forEachIndexed { index, _ ->
        input.uniqueSubsetIndex(index, 4)?.let { return it }
    }
    return 0
}

fun partTwo(): Int {
    val input = inputParser.lines().first()
    input.forEachIndexed { index, _ ->
        input.uniqueSubsetIndex(index, 14)?.let { return it }
    }
    return 0
}

fun String.uniqueSubsetIndex(index: Int, span: Int): Int? {
    val section = this.substring(index until index + span)
    return if (section.toCharArray().toSet().size == span) {
        index + span
    } else {
        null
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
