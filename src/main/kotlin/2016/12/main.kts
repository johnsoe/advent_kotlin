import `2016`.assembunny.AssembunnyInterpreter
import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/12/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val registers = mutableMapOf(
        "a" to 0,
        "b" to 0,
        "c" to 0,
        "d" to 0
    )
    AssembunnyInterpreter.parseInstructions(
        inputParser.linesOfType<String>(),
        registers
    )
    return registers["a"] ?: 0
}

fun partTwo(): Int {
    val registers = mutableMapOf(
        "a" to 0,
        "b" to 0,
        "c" to 1,
        "d" to 0
    )
    AssembunnyInterpreter.parseInstructions(
        inputParser.linesOfType<String>(),
        registers
    )
    return registers["a"] ?: 0
}
