package `2023`.`04`

import util.InputParser
import util.string.toInts
import kotlin.Int
import kotlin.math.pow

val inputParser = InputParser("2023/04/input.txt")
val cardMatches = inputParser.lines().map { line ->
    val split = line.substring(10).split(" | ")
    val winningNums = split.first().toInts()
    val availableNums = split.last().toInts()
    winningNums.intersect(availableNums).size
}



fun partOne(): Int {
    return cardMatches
        .filter { it > 0 }
        .sumOf { 2.0.pow((it - 1).toDouble()) }
        .toInt()
}

fun partTwo(): Int {
    val counts = cardMatches.indices.associateWith { 1 }.toMutableMap()
    cardMatches.forEachIndexed { index, matches ->
        val count = counts[index]!!
        for (i in (index + 1)..(index+matches)) {
            counts[i] = counts[i]!! + count
        }
    }
    return counts.values.sum()
}

fun main() {
    println(partOne())
    println(partTwo())
}
