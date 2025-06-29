package `2024`.`01`

import util.InputParser
import java.util.PriorityQueue
import kotlin.Int
import kotlin.math.abs

private val inputParser = InputParser("2024/01/input.txt")

private fun partOne(): Int {
    val capacity = inputParser.lines().size
    val firstCol = PriorityQueue<Int>(capacity)
    val secondCol = PriorityQueue<Int>(capacity)
    inputToIntsList().forEach {
        firstCol.add(it.first)
        secondCol.add(it.second)
    }
    var sum = 0
    while (firstCol.isNotEmpty()) {
        sum += abs(firstCol.remove() - secondCol.remove())
    }
    return sum
}

private fun inputToIntsList(): List<Pair<Int, Int>> {
    return inputParser.lines().map {
        val split = it.split("   ")
        split.first().toInt() to split.last().toInt()
    }
}

private fun partTwo(): Int {
    val cols = inputToIntsList()
    val secondCol = cols
        .groupingBy { (_, key) -> key }
        .eachCount()
    return cols.sumOf { (key, _) ->
        key * secondCol.getOrDefault(key, 0)
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
