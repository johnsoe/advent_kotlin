package `2022`.`17`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2022/17/input.txt")

private fun partOne(): Int {
    val rows = mutableMapOf<Long, Array<Boolean>>()
        .apply { this[0] = createNewRow() }
    val wind = inputParser.lines().first().toCharArray()

    var windIndex = 0L
    var rockIndex = 0L
    var peak = 0L
    val rockOrder = getOrderedRocks()
    repeat(2022) {
        val rowMax = rows.maxOf { it.key }
        repeat((rowMax - peak).toInt()) {
            rows[peak + it] = createNewRow()
        }
    }

    return 0
}

fun getOrderedRocks() = listOf(Rock.Line, Rock.Cross, Rock.Elbow, Rock.Vert, Rock.Square)

fun createNewRow(): Array<Boolean> {
    return Array<Boolean>(7) { false }
}

private fun partTwo(): Int {
    return 0
}

private fun main() {
    println(partOne())
    println(partTwo())
    println("partOne")
    println("partTwo")
}
