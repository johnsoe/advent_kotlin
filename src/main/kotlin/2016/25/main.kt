package `2016`.`25`

import `2016`.assembunny.AssembunnyInterpreter
import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2016/25/input.txt")

private fun partOne(): Int {
    var initA = 0
    while (true) {
        val registers = mutableMapOf(
            "a" to initA,
            "b" to 0,
            "c" to 0,
            "d" to 0,
        )
        var next = 1
        println(initA)
        AssembunnyInterpreter.parseInstructions(
            inputParser.linesOfType<String>(),
            registers,
        ) {
            next = (next + 1) % 2
            it == next
        }
        initA++
    }
}

private fun main() {
    println(partOne())
}
