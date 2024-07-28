import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/18/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    return getTotalSafeCount(40)
}

fun partTwo(): Long {
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
