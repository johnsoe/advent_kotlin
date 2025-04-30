package `2015`.`05`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2015/05/input.txt")
val naughtyList = listOf("ab", "cd", "pq", "xy")

fun partOne(): Int {
    return inputParser.lines()
        .count { it.isNice() }
}

fun String.isNice(): Boolean {
    val vowelCount = this.count { it in "aeiou" }
    val hasDupe = this.drop(1).withIndex()
        .find {
            it.value == this[it.index]
        } != null
    val isNotNaughty = naughtyList.all {
        !this.contains(it)
    }
    return vowelCount >= 3 && hasDupe && isNotNaughty
}

fun partTwo(): Int {
    return inputParser.lines()
        .count { it.isActuallyNice() }
}

fun String.isActuallyNice(): Boolean {
    val hasOverlap = this.drop(2).withIndex().any {
        this[it.index] == it.value && this[it.index + 1] != it.value
    }
    val pairs = mutableListOf<String>()
    val hasValidPair = this.drop(1).withIndex().any {
        val nextPair = "${it.value}${this[it.index]}"
        val result = if (nextPair in pairs) {
            nextPair != pairs.last()
        } else {
            false
        }
        pairs.add(nextPair)
        result
    }
    return hasOverlap && hasValidPair
}

fun main() {
    println(partOne())
    println(partTwo())
}
