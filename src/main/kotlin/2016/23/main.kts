import `2016`.assembunny.AssembunnyInterpreter
import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/23/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val registers = mutableMapOf(
        "a" to 7,
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

// There must be a smarter way to do this
fun partTwo(): Int {
    val registers = mutableMapOf(
        "a" to 12,
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
