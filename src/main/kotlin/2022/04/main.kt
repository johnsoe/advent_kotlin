package `2022`.`04`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2022/04/input.txt")

private fun partOne(): Int {
    return inputParser.lines()
        .map { parseInputAsPair(it) }
        .count { it.first.containsOther(it.second) || it.second.containsOther(it.first) }
}

private fun partTwo(): Int {
    return inputParser.lines()
        .map { parseInputAsPair(it) }
        .count { it.first.overlapsOther(it.second) || it.second.overlapsOther(it.first) }
}

private fun parseInputAsPair(input: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val ints = input.split("-", ",")
        .map { it.toInt() }
    return (ints[0] to ints[1]) to (ints[2] to ints[3])
}

private fun Pair<Int, Int>.containsOther(other: Pair<Int, Int>): Boolean {
    return this.first <= other.first && this.second >= other.second
}

private fun Pair<Int, Int>.overlapsOther(other: Pair<Int, Int>): Boolean {
    return (this.first <= other.first && this.first >= other.first) ||
        (this.second >= other.first && this.second <= other.second)
}

private fun main() {
    println(partOne())
    println(partTwo())
}
