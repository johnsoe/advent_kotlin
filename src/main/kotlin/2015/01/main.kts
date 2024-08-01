import kotlin.Int
import util.InputParser

val inputParser = InputParser("2015/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val input = inputParser.line()
    return input.count { it == '(' } - input.count { it == ')' }
}

fun partTwo(): Int {
    var count = 0
    inputParser.line().forEachIndexed { index, c ->
        if (c == '(') count++ else count--
        if (count < 0) {
            return index + 1
        }
    }
    return -1
}
