package `2019`.`01`

import util.InputParser

private val inputParser = InputParser("2019/01/input.txt")

private fun partOne(): Long {
    return inputParser.linesLong()
        .sumOf { calcFuel(it) }
}

fun calcFuel(mass: Long): Long = mass / 3 - 2

private fun partTwo(): Long {
    return inputParser.linesLong()
        .sumOf { calcIterFuel(it) }
}

private fun calcIterFuel(mass: Long): Long {
    val fuel = calcFuel(mass)
    return if (fuel <= 0) {
        0
    } else {
        fuel + calcIterFuel(fuel)
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
