package `2016`.`18`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2016/18/input.txt")

private fun partOne(): Long {
    return getTotalSafeCount(40)
}

private fun partTwo(): Long {
    return getTotalSafeCount(400000)
}

fun getTotalSafeCount(rowCount: Int): Long {
    var row = inputParser.line()
    var sum = row.getSafeCount()
    repeat(rowCount - 1) {
        row = row.generateNextRow()
        sum += row.getSafeCount()
    }
    return sum
}

private fun String.generateNextRow(): String {
    val bufferedRow = ".$this."
    var next = ""
    this.forEachIndexed { index, c ->
        val window = bufferedRow.substring(index, index + 3)
        next += when (window) {
            "^^.", ".^^", "^..", "..^" -> '^'
            else -> '.'
        }
    }
    return next
}

private fun String.getSafeCount(): Long {
    return this.count { it == '.' }.toLong()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
