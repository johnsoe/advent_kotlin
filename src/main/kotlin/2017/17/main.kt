package `2017`.`17`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2017/17/input.txt")

fun partOne(): Int {
    val step = inputParser.line().toInt()
    val buffer = mutableListOf(0)
    var position = 0
    repeat(2017) {
        position = (step + position) % buffer.size
        buffer.add(position + 1, it + 1)
        position++
    }
    return buffer[position + 1]
}

// Remove buffer, instead just track what is currently in front of the 0
fun partTwo(): Int {
    val step = inputParser.line().toInt()
    var size = 1
    var position = 0
    var nextToFront = 0
    repeat(50_000_000) {
        position = (step + position) % size
        if (position == 0) nextToFront = it + 1
        size++
        position++
    }
    return nextToFront
}

fun main() {
    println(partOne())
    println(partTwo())
}
