package `2022`.`04`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2022/04/input.txt")



fun partOne(): Int {
    return inputParser.lines()
        .map { parseInputAsPair(it) }
        .count { it.first.containsOther(it.second) || it.second.containsOther(it.first) }
}

fun partTwo(): Int {
    return inputParser.lines()
        .map { parseInputAsPair(it) }
        .count { it.first.overlapsOther(it.second) || it.second.overlapsOther(it.first) }
}

fun parseInputAsPair(input: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val ints = input.split("-", ",")
        .map { it.toInt() }
    return (ints[0] to ints[1]) to (ints[2] to ints[3])
}

fun Pair<Int, Int>.containsOther(other: Pair<Int, Int>): Boolean {
    return this.first <= other.first && this.second >= other.second
}

fun Pair<Int, Int>.overlapsOther(other: Pair<Int, Int>): Boolean {
    return (this.first <= other.first && this.first >= other.first) ||
        (this.second >= other.first && this.second <= other.second)
}

fun main() {
    println(partOne())
    println(partTwo())
}
