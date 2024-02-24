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
    return parseInstructions(inputParser.linesOfType<String>(), registers)
}

fun parseInstructions(instructions: List<List<String>>, registers: MutableMap<String, Int>): Int {
    var instructionPtr = 0
    while (instructionPtr < instructions.size) {
        val instruction = instructions[instructionPtr]
        when(instruction.first()) {
            "cpy" -> {
                val register = instruction.last()
                registers[register] = instruction[1].parseRegisterInt(registers)
            }
            "inc" -> {
                registers.add(instruction.last(), 1)
            }
            "dec" -> {
                registers.add(instruction.last(), -1)
            }
            "jnz" -> {
                val jumpAmt = instruction.last().toInt()
                if (instruction[1].parseRegisterInt(registers) != 0) {
                    instructionPtr += (jumpAmt - 1)
                }
            }
        }
        instructionPtr++
    }
    return registers["a"] ?: 0
}

private fun MutableMap<String, Int>.add(key: String, toAdd: Int) {
    this[key] = this[key]?.plus(toAdd) ?: 0
}

private fun String.parseRegisterInt(registers: Map<String, Int>): Int {
    val amt = this.toIntOrNull()
    return amt ?: (registers[this] ?: 0)
}

fun partTwo(): Int {
    val registers = mutableMapOf(
        "a" to 0,
        "b" to 0,
        "c" to 1,
        "d" to 0
    )
    return parseInstructions(inputParser.linesOfType<String>(), registers)
}
