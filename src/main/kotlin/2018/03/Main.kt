package `2018`.`03`

import kotlin.Int
import util.InputParser

private val inputParser: InputParser = InputParser("2018/03/input.txt")

/**
 * --- Day 3: No Matter How You Slice It ---
 * Completed with the help of Github Copilot
 */
private fun partOne(): Int {
    val claims = inputParser.lines() // Reads the list of claims from the input file
    val fabric = Array(1000) { IntArray(1000) } // 1000x1000 fabric grid
    var overlapCount = 0

    for (claim in claims) {
        val parts = """#\d+ @ (\d+),(\d+): (\d+)x(\d+)""".toRegex().find(claim)!!.destructured
        val (left, top, width, height) = parts.toList().map { it.toInt() }

        for (i in top until top + height) {
            for (j in left until left + width) {
                fabric[i][j]++
                if (fabric[i][j] == 2) overlapCount++ // Count only when a square is claimed by 2 or more
            }
        }
    }

    return overlapCount
}

private fun partTwo(): Int {
    val claims = inputParser.lines() // Reads the list of claims from the input file
    val fabric = Array(1000) { IntArray(1000) } // 1000x1000 fabric grid
    val claimIds = mutableSetOf<Int>()

    // First pass: Mark the fabric and track all claim IDs
    for (claim in claims) {
        val parts = """#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""".toRegex().find(claim)!!.destructured
        val (id, left, top, width, height) = parts.toList().map { it.toInt() }
        claimIds.add(id)

        for (i in top until top + height) {
            for (j in left until left + width) {
                if (fabric[i][j] != 0) {
                    claimIds.remove(fabric[i][j]) // Remove overlapping claim
                    claimIds.remove(id) // Remove current claim
                }
                fabric[i][j] = id
            }
        }
    }

    // The remaining claim ID in the set is the one that doesn't overlap
    return claimIds.first()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
