package `2023`.`02`

import util.InputParser
import kotlin.Int
import kotlin.math.max

private val inputParser = InputParser("2023/02/input.txt")

private fun partOne(): Int {
    val limit = Round(12, 13, 14)
    val validGameSum = inputParser.lines().map {
        it.toGame()
    }.filter {
        it.rounds.all { round ->
            round.redCount <= limit.redCount &&
                round.blueCount <= limit.blueCount &&
                round.greenCount <= limit.greenCount
        }
    }.sumOf {
        it.id
    }
    return validGameSum
}

private fun partTwo(): Int {
    return inputParser.lines().map { it.toGame() }
        .map {
            it.rounds.fold(Round()) { acc, curRound ->
                acc.redCount = max(acc.redCount, curRound.redCount)
                acc.greenCount = max(acc.greenCount, curRound.greenCount)
                acc.blueCount = max(acc.blueCount, curRound.blueCount)
                acc
            }
        }
        .sumOf {
            it.redCount * it.greenCount * it.blueCount
        }
}

fun String.toGame(): Game {
    val gameSplit = this.split(":", ";")
    return Game(
        id = gameSplit.first().split(" ").last().toInt(),
        rounds = gameSplit.drop(1).map { it.trim().toRound() },
    )
}

fun String.toRound(): Round {
    val roundSplit = this.split(" ", ", ")
    var current = 0
    val round = Round()
    roundSplit.forEach {
        it.toIntOrNull()?.let { count ->
            current = count
        } ?: run {
            when (it) {
                "red" -> round.redCount = current
                "green" -> round.greenCount = current
                "blue" -> round.blueCount = current
            }
        }
    }
    return round
}

data class Game(
    val id: Int,
    val rounds: List<Round>,
)

data class Round(
    var redCount: Int = 0,
    var greenCount: Int = 0,
    var blueCount: Int = 0,
)

private fun main() {
    println(partOne())
    println(partTwo())
}
