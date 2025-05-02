package `2017`.`02`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/02/input.txt")

private fun partOne(): Int {
    return inputParser.linesOfType<Int>(" ", "\t")
        .sumOf { it.max() - it.min() }
}

private fun partTwo(): Int {
    return inputParser.linesOfType<Int>(" ", "\t")
        .sumOf { nums ->
            nums.mapNotNull { i ->
                nums.find { j ->
                    i != j && i % j == 0
                }?.let {
                    i to it
                }
            }.sumOf {
                it.first / it.second
            }
        }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
