import `2019`.opcode.Computer
import kotlin.Int
import util.InputParser

val inputParser = InputParser("2019/2/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val opcodes = inputParser.linesBySeparator()
        .map { it.toInt() }
        .toIntArray()
    opcodes[1] = 12
    opcodes[2] = 2
    return Computer(opcodes).evaluate()
}

fun partTwo(): Int {
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
