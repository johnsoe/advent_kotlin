package `2018`.`02`

import kotlin.Int
import util.InputParser

private val inputParser: InputParser = InputParser("2018/02/input.txt")

/**
 * --- Day 2: Inventory Management System ---
 * Completed with the help of Github Copilot
 */
private fun partOne(): Int {
    val boxIds = inputParser.lines() // Reads the list of box IDs from the input file
    var countTwos = 0
    var countThrees = 0

    for (id in boxIds) {
        val charCounts = id.groupingBy { it }.eachCount() // Counts occurrences of each character
        if (charCounts.containsValue(2)) countTwos++ // Increment if any character appears exactly twice
        if (charCounts.containsValue(3)) countThrees++ // Increment if any character appears exactly three times
    }

    return countTwos * countThrees // Calculate the checksum
}

private fun partTwo(): String {
    val boxIds = inputParser.lines() // Reads the list of box IDs from the input file

    for (i in boxIds.indices) {
        for (j in i + 1 until boxIds.size) {
            val id1 = boxIds[i]
            val id2 = boxIds[j]
            val commonChars = StringBuilder()
            var differences = 0

            for (k in id1.indices) {
                if (id1[k] == id2[k]) {
                    commonChars.append(id1[k])
                } else {
                    differences++
                }

                if (differences > 1) break
            }

            if (differences == 1) {
                return commonChars.toString() // Return the common characters
            }
        }
    }
    throw IllegalStateException("No matching box IDs found")
}

private fun main() {
    println(partOne())
    println(partTwo())
}
