package `2023`.`07`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2023/07/input.txt")

private fun partOne() = calculateTotalWinnings(allowJokers = false)
private fun partTwo() = calculateTotalWinnings(allowJokers = true)

fun calculateTotalWinnings(allowJokers: Boolean): Int {
    return inputParser.lines()
        .map { input ->
            val handStrs = input.split(" ")
            val bid = handStrs.last().toInt()
            val hand = handStrs.first()
                .map { Card.charToCard(it, allowJokers) }
                .toTypedArray()

            Hand(bid, hand)
        }
        .sortedWith(compareBy { it })
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
