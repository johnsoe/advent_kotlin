package `2024`.`02`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2024/02/input.txt")

private fun partOne(): Int {
    return inputParser.linesOfType<Int>()
        .count { it.isSafe() || it.reversed().isSafe() }
}

private fun List<Int>.isSafe(): Boolean {
    return this.drop(1).withIndex().all {
        val prev = this[it.index]
        val diff = it.value - prev
        diff in 1..3
    }
}

private fun partTwo(): Int {
    return inputParser.linesOfType<Int>()
        .count { it.isSafeish() || it.reversed().isSafeish() }
}

private fun List<Int>.isSafeish(): Boolean {
    return this.withIndex().any {
        val inner = this.toMutableList()
        inner.removeAt(it.index)
        inner.isSafe()
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
