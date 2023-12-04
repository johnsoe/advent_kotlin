import kotlin.Int
import util.InputParser
import util.splitInHalf

val inputParser = InputParser("2022/03/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return inputParser.lines()
        .asSequence()
        .map { it.splitInHalf() }
        .map { it.first.toCharArray().intersect(it.second.toCharArray().toSet()) }
        .map { it.first() }
        .sumOf { it.mapToPriority() }
}

fun Char.mapToPriority(): Int {
    return if (this.isLowerCase()) {
        this.code - 96
    } else {
        this.code - 38
    }
}

fun partTwo(): Int {
    return inputParser.lines()
        .chunked(3)
        .map {
            it.map { ruck -> ruck.toCharArray() }
                .reduce { acc, chars ->
                    acc.intersect(chars.toSet()).toCharArray()
                }
        }
        .map { it.first() }
        .sumOf { it.mapToPriority() }
}
