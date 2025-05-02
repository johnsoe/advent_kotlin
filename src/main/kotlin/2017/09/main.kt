package `2017`.`09`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/09/input.txt")

private fun partOne() = takeOutTheTrash().first
private fun partTwo() = takeOutTheTrash().second

fun takeOutTheTrash(): Pair<Int, Int> {
    val input = inputParser.line()
    var groups = 0
    var depth = 0
    var index = 0
    var trashCount = 0
    var inTrash = false
    while (index < input.length) {
        val next = input[index]
        when {
            inTrash && next == '!' -> index++
            inTrash && next == '>' -> inTrash = false
            inTrash -> trashCount++
            next == '<' -> inTrash = true
            next == '{' -> depth++
            next == '}' -> {
                groups += depth
                depth--
            }
        }
        index++
    }
    return groups to trashCount
}

private fun main() {
    println(partOne())
    println(partTwo())
}
