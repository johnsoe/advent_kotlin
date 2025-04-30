package `2017`.`16`

import util.InputParser
import util.list.*

val inputParser = InputParser("2017/16/input.txt")

fun partOne(): String {
    val arr = Array(16) { (it + 97).toChar() }
    arr.parseInput()
    return arr.shortContentString()
}

private fun Array<Char>.parseInput() {
    inputParser.line().split(",")
        .forEach {
            when (it.first()) {
                's' -> {
                    val offset = it.drop(1).toInt()
                    this.rotate(offset)
                }
                'x' -> {
                    val indices = it.drop(1).split('/').map { it.toInt() }
                    this.swapIndices(indices.first(), indices.last())
                }
                'p' -> {
                    val values = it.drop(1).split('/').map { it.first() }
                    this.swapValues(values.first(), values.last())
                }
            }
        }
}

fun partTwo(): String {
    val arr = Array(16) { (it + 97).toChar() }
    var state = arr.shortContentString()
    val states = mutableMapOf<String, String>()
    // Could probably break early once a cycle is detected and count states. This works too.
    repeat(1_000_000_000) {
        if (state !in states) {
            state.forEachIndexed { index, c ->
                arr[index] = c
            }
            arr.parseInput()
            states[state] = arr.shortContentString()
        }
        state = states[state]!!
    }
    return state
}

fun main() {
    println(partOne())
    println(partTwo())
}
