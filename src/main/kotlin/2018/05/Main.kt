package `2018`.`05`

import kotlin.Int
import util.InputParser

private val inputParser: InputParser = InputParser("2018/05/input.txt")

/**
 * --- Day 5: Alchemical Reduction ---
 *
 * Completed with the help of Github Copilot (2 Attempts)
 */
private fun partOne(): Int {
    val polymer = inputParser.line().trim()
    val stack = ArrayDeque<Char>()

    for (unit in polymer) {
        if (stack.isNotEmpty() && reacts(stack.last(), unit)) {
            stack.removeLast()
        } else {
            stack.addLast(unit)
        }
    }

    return stack.size
}

private fun reacts(a: Char, b: Char): Boolean {
    return a != b && a.equals(b, ignoreCase = true)
}

private fun partTwo(): Int {
    val polymer = inputParser.line().trim()
    val unitTypes = polymer.lowercase().toSet() // Get all unique unit types (case-insensitive)
    var shortestLength = Int.MAX_VALUE

    for (unitType in unitTypes) {
        val filteredPolymer = polymer.filter { it.equals(unitType, ignoreCase = true).not() }
        val reactedLength = fullyReact(filteredPolymer)
        shortestLength = minOf(shortestLength, reactedLength)
    }

    return shortestLength
}

private fun fullyReact(polymer: String): Int {
    val stack = ArrayDeque<Char>()

    for (unit in polymer) {
        if (stack.isNotEmpty() && reacts(stack.last(), unit)) {
            stack.removeLast()
        } else {
            stack.addLast(unit)
        }
    }
    return stack.size
}

private fun main() {
    println(partOne())
    println(partTwo())
}
