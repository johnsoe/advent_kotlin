package `2019`.`04`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2019/04/input.txt")

private fun partOne(): Int {
    return getInputRange().filter {
        neverDecreases(it.toString()) && hasDouble(it.toString())
    }.size
}

private fun getInputRange(): IntRange {
    val input = inputParser.lines()
        .first()
        .split('-')
        .map { it.toInt() }
    return IntRange(input.first(), input.last())
}

private fun partTwo(): Int {
    return getInputRange().filter {
        neverDecreases(it.toString()) && hasStrictDouble(it.toString())
    }.size
}

private fun neverDecreases(pwd: String): Boolean {
    return !pwd.dropLast(1)
        .withIndex()
        .any { (index, c) ->
            c > pwd[index + 1]
        }
}

private fun hasDouble(pwd: String): Boolean {
    return pwd.dropLast(1)
        .withIndex()
        .any { (index, c) ->
            c == pwd[index + 1]
        }
}

private fun hasStrictDouble(pwd: String): Boolean {
    var i = 0
    while (i + 1 < pwd.length) {
        var match = 0
        var j = i + 1
        while (j < pwd.length && pwd[j] == pwd[i]) {
            j++
            match++
        }
        if (match == 1) {
            return true
        }
        i += match + 1
    }
    return false
}

private fun main() {
    println(partOne())
    println(partTwo())
}
