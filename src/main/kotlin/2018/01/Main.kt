package `2018`.`01`

import kotlin.Int
import util.InputParser

/**
 * --- Day 1: Chronal Calibration ---
 * Completed with the help of Github Copilot
 */
private val inputParser: InputParser = InputParser("2018/01/input.txt")

private fun partOne(): Int {
    return inputParser.linesInt().sum()
}

private fun partTwo(): Int {
    val input = inputParser.linesInt()
    val seenFrequencies = mutableSetOf(0)
    var currentFrequency = 0
    var firstRepeatedFrequency: Int? = null

    while (firstRepeatedFrequency == null) {
        for (change in input) {
            currentFrequency += change
            if (!seenFrequencies.add(currentFrequency)) {
                firstRepeatedFrequency = currentFrequency
                break
            }
        }
    }

    return firstRepeatedFrequency
}

private fun main() {
    println(partOne())
    println(partTwo())
}
