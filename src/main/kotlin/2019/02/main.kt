package `2019`.`02`

import `2019`.opcode.Computer
import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2019/02/input.txt")

private fun partOne(): Int {
    val opcodes = inputParser.linesBySeparator()
        .map { it.toInt() }
        .toIntArray()
    opcodes[1] = 12
    opcodes[2] = 2
    return Computer(opcodes).evaluate()
}

private fun partTwo(): Int {
    val target = 19690720
    val opcodes = inputParser.linesBySeparator()
        .map { it.toInt() }
        .toIntArray()
    for (noun in 0..99) {
        for (verb in 0..99) {
            val reset = opcodes.clone()
            reset[1] = noun
            reset[2] = verb
            val result = Computer(reset).evaluate()
            if (result == target) {
                return 100 * noun + verb
            }
        }
    }
    return 0
}

private fun main() {
    println(partOne())
    println(partTwo())
}
