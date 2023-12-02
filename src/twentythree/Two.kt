package twentythree

import InputParser
import twentyone.Fourteen
import kotlin.math.max
import kotlin.math.round

object Two : InputParser<Int>() {
    public override fun getFileName(): String = "twentythree/input/two.txt"

    override fun partOne(): Int {
        val limit = Round(12, 13, 14)
        val validGameSum = getInputByLine().map {
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

    override fun partTwo(): Int {
        return getInputByLine().map { it.toGame() }
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

    private fun String.toGame(): Game {
        val gameSplit = this.split(":", ";")
        return Game(
            id = gameSplit.first().split(" ").last().toInt(),
            rounds = gameSplit.drop(1).map { it.trim().toRound() }
        )
    }

    private fun String.toRound(): Round {
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

    private data class Game(
        val id: Int,
        val rounds: List<Round>
    )

    private data class Round(
        var redCount: Int = 0,
        var greenCount: Int = 0,
        var blueCount: Int = 0
    )
}

public fun main(): Unit {
    println(Two.partOne())
    println(Two.partTwo())
}