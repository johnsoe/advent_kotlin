import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/19/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val patterns = inputParser.lines().first().split(",").map { it.trim() }
    return inputParser.lines().drop(2).count {
        it.isPossible(patterns)
    }
}

fun String.isPossible(patterns: List<String>): Boolean {
    return if (this.isEmpty()) {
        true
    } else {
        patterns.any {
            if (this.startsWith(it)) {
                this.drop(it.length).isPossible(patterns)
            } else false
        }
    }
}

fun partTwo(): Long {
    val patterns = inputParser.lines().first().split(",").map { it.trim() }
    return inputParser.lines().drop(2)
        .sumOf {
            it.possibleCounts(patterns, mutableMapOf())
        }
}

fun String.possibleCounts(patterns: List<String>, found: MutableMap<String, Long>): Long {
    found[this]?.let { return it }
    return if (this.isEmpty()) {
        1
    } else {
        val subs = patterns.sumOf {
            if (this.startsWith(it)) {
                this.drop(it.length).possibleCounts(patterns, found)
            } else {
                0
            }
        }
        found[this] = subs
        subs
    }
}
