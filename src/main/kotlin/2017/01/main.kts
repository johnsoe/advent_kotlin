import kotlin.Int
import util.InputParser

val inputParser = InputParser("2017/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val input = inputParser.line()
    return input.matchedSums(1)
}

private fun String.matchedSums(offset: Int): Int {
    return this
        .withIndex()
        .filter { it.value == this[(it.index + offset) % this.length] }
        .sumOf { it.value.digitToInt() }
}

fun partTwo(): Int {
    val input = inputParser.line()
    return input.matchedSums(input.length / 2)
}
