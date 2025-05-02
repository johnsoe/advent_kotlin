package `2017`.`05`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/05/input.txt")

private fun partOne(): Int {
    return inputParser.linesInt()
        .toMutableList()
        .runJumps { 1 }
}

private fun partTwo(): Int {
    val jumps = inputParser.linesInt().toMutableList()
    return jumps.runJumps {
        if (jumps[it] >= 3) {
            -1
        } else {
            1
        }
    }
}

private fun MutableList<Int>.runJumps(diff: (Int) -> Int): Int {
    var count = 0
    var index = 0
    while (index < this.size) {
        val offset = diff.invoke(index)
        this[index] += offset
        index += this[index] - offset
        count++
    }
    return count
}

private fun main() {
    println(partOne())
    println(partTwo())
}
