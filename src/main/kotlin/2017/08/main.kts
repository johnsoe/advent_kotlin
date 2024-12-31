import kotlin.Int
import util.InputParser
import kotlin.math.max

val inputParser = InputParser("2017/08/input.txt")

val inputRegex = """(.+) (.+) (-?\d+) if (.+) (.+) (-?\d+)""".toRegex()
println(partOne())
println(partTwo())

fun partOne() = parseInstructions().first
fun partTwo() = parseInstructions().second

private fun String.op(a: Int, b: Int): Boolean {
    return when (this) {
        ">" -> a > b
        ">=" -> a >= b
        "==" -> a == b
        "<" -> a < b
        "<=" -> a <= b
        "!=" -> a != b
        else -> throw UnsupportedOperationException()
    }
}

private fun parseInstructions(): Pair<Int, Int> {
    val registers = mutableMapOf<String, Int>()
    var max = 0
    inputParser.lines().forEach {
        val command = inputRegex.find(it)?.destructured?.toList() ?: emptyList()
        var checkReg = command[3]
        registers.putIfAbsent(checkReg, 0)
        if (command[4].op(registers[checkReg]!!, command[5].toInt())) {
            val delta = command[2].toInt() * if (command[1] == "dec") -1 else 1
            checkReg = command[0]
            registers.putIfAbsent(checkReg, 0)
            registers[checkReg] = registers[checkReg]!! + delta
            max = max(registers[checkReg]!!, max)
        }
    }
    return max to registers.values.max()
}
