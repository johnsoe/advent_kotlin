package `2016`.`23`

import `2016`.assembunny.AssembunnyInterpreter
import util.InputParser
import kotlin.Int

val inputParser = InputParser("2016/23/input.txt")

fun partOne(): Int {
    val registers = mutableMapOf(
        "a" to 7,
        "b" to 0,
        "c" to 0,
        "d" to 0,
    )
    AssembunnyInterpreter.parseInstructions(
        inputParser.linesOfType<String>(),
        registers,
    )
    return registers["a"] ?: 0
}

// There must be a smarter way to do this
fun partTwo(): Int {
    val registers = mutableMapOf(
        "a" to 12,
        "b" to 0,
        "c" to 0,
        "d" to 0,
    )
    AssembunnyInterpreter.parseInstructions(
        inputParser.linesOfType<String>(),
        registers,
    )
    return registers["a"] ?: 0
}

fun main() {
    println(partOne())
    println(partTwo())
}
