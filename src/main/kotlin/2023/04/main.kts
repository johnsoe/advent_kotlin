import util.InputParser
import kotlin.Int
import kotlin.math.pow

val inputParser = InputParser("2023/04/input.txt")
val winningCounts = inputParser.lines().map { line ->
    val split = line.substring(10).split(" | ")
    val winningNums = split.first().trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
    val availableNums = split.last().trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
    winningNums.intersect(availableNums).size
}
println(partOne())
println(partTwo())

fun partOne(): Int {
    return winningCounts
        .filter { it > 0 }
        .sumOf { 2.0.pow((it - 1).toDouble()) }
        .toInt()
}

fun partTwo(): Int {
    val counts = winningCounts.indices.associateWith { 1 }.toMutableMap()
    winningCounts.forEachIndexed { index, matches ->
        val count = counts[index]!!
        for (i in (index + 1)..(index+matches)) {
            counts[i] = counts[i]!! + count
        }
    }
    return counts.values.sum()
}
