package `2024`.`25`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2024/25/input.txt")

private fun partOne(): Int {
    val input = inputParser.chunk().map { grid ->
        val counts = IntArray(5)
        val type = if (grid.first().all { it == '#' }) {
            "LOCK"
        } else {
            "KEY"
        }
        grid.forEach { row ->
            row.forEachIndexed { index, digit ->
                if (digit == '#') {
                    counts[index]++
                }
            }
        }
        type to counts
    }
    val locks = input.filter { it.first == "LOCK" }.map { it.second }
    val keys = input.filter { it.first == "KEY" }.map { it.second }

    return locks.sumOf { lock ->
        keys.count { key ->
            key.withIndex()
                .all { it.value + lock[it.index] <= 7 }
        }
    }
}

private fun partTwo(): Int {
    return 0
}

private fun main() {
    println(partOne())
    println(partTwo())
    println("partTwo")
}
