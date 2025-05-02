package `2015`.`01`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2015/01/input.txt")

private fun partOne(): Int {
    val input = inputParser.line()
    return input.count { it == '(' } - input.count { it == ')' }
}

private fun partTwo(): Int {
    var count = 0
    inputParser.line().forEachIndexed { index, c ->
        if (c == '(') count++ else count--
        if (count < 0) {
            return index + 1
        }
    }
    return -1
}

private fun main() {
    println(partOne())
    println(partTwo())
}
