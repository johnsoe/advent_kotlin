import kotlin.Int
import util.InputParser

val inputParser = InputParser("2022/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split(" ") }
        .map { it.sumOf { num -> num.toInt() } }
        .max()
}

fun partTwo(): Int {
    return inputParser.chunkAndJoin()
        .map { it.split( " ") }
        .map { it.sumOf { num -> num.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()
}
