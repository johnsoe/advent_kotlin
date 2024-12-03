import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/03/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val mulRegex = """mul\((\d+),(\d+)\)""".toRegex()
    return inputParser.lines().sumOf { input ->
        mulRegex.findAll(input).sumOf {
            it.getMul()
        }
    }
}

private fun MatchResult.getMul(): Int {
    val ints = this.destructured
        .toList()
        .map { it.toInt() }
    return ints.first() * ints.last()
}

fun partTwo(): Int {
    val mulRegex = """mul\((\d+),(\d+)\)|do\(\)|don't\(\)""".toRegex()
    var isActive = true
    return inputParser.lines().sumOf { input ->
        mulRegex.findAll(input).sumOf { match ->
            val first = match.groupValues.first()
            when {
                first.startsWith("don") -> {
                    isActive = false
                    0
                }
                first.startsWith("do") -> {
                    isActive = true
                    0
                }
                isActive -> match.getMul()
                else -> 0
            }
        }
    }
}
