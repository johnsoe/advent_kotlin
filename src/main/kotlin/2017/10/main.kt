package `2017`.`10`

import util.InputParser
import util.hash.knotReverse
import util.hash.toKnotHash
import kotlin.Int

private val inputParser = InputParser("2017/10/input.txt")

private fun partOne(): Int {
    var index = 0
    val arr = IntArray(256) { it }
    inputParser.linesOfType<Int>(",")
        .flatten()
        .forEachIndexed { skip, sub ->
            arr.knotReverse(index, (index + sub) % 256)
            index = (index + sub + skip) % 256
        }
    return arr[0] * arr[1]
}

private fun partTwo() = inputParser.line().toKnotHash()

private fun main() {
    println(partOne())
    println(partTwo())
}
