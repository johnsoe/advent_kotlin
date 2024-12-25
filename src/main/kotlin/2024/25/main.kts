import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/25/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
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

fun partTwo(): Int {
    println("partTwo")
    return 0
}
