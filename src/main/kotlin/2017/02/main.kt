package `2017`.`02`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2017/02/input.txt")



fun partOne(): Int {
    return inputParser.linesOfType<Int>(" ", "\t")
        .sumOf { it.max() - it.min() }
}

fun partTwo(): Int {
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

fun main() {
    println(partOne())
    println(partTwo())
}
