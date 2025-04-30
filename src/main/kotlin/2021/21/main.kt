package `2021`.`21`

import util.InputParser
import util.math.mult
import kotlin.Int
import kotlin.math.min

val inputParser = InputParser("2021/21/input.txt")

fun partOne(): Long {
    val (firstPlayer, secondPlayer) = getPlayers()
    var count = 1
    var turn = 1
    while (firstPlayer.score < 1000 && secondPlayer.score < 1000) {
        val currentPlayer = if (turn % 2 == 0) {
            secondPlayer
        } else {
            firstPlayer
        }
        currentPlayer.currentPosition = (currentPlayer.currentPosition + nextRolls(count) - 1) % 10 + 1
        currentPlayer.score += currentPlayer.currentPosition
        count = (2 + count) % 100 + 1
        turn++
    }
    println((turn - 1) * 3)
    println(firstPlayer)
    println(secondPlayer)
    return (((turn - 1) * 3) * min(firstPlayer.score, secondPlayer.score)).toLong()
}

fun nextRolls(currentCount: Int): Int {
    return listOf(
        (currentCount - 1) % 100 + 1,
        currentCount % 100 + 1,
        (currentCount + 1) % 100 + 1,
    ).sum()
}

fun getPlayers(): Pair<Player, Player> {
    val startPositions = inputParser.lines().map {
        it.split(": ").last().toInt()
    }
    return Player(startPositions.first()) to Player(startPositions.last())
}

fun partTwo(): Long {
    val (firstPlayer, secondPlayer) = getPlayers()
    val perms = getAllPermutations(firstPlayer, secondPlayer, true)
    return java.lang.Long.max(perms.first, perms.second)
}

fun getAllPermutations(
    playerOne: Player,
    playerTwo: Player,
    playerOneTurn: Boolean,
): Pair<Long, Long> {
    if (playerOne.score >= 21) {
        return getPermutationsFromHistory(playerOne.rollHistory, playerTwo.rollHistory) to 0L
    } else if (playerTwo.score >= 21) {
        return 0L to getPermutationsFromHistory(playerOne.rollHistory, playerTwo.rollHistory)
    } else {
        val currentPlayer = if (playerOneTurn) playerOne else playerTwo
        var firstSubPaths = 0L
        var secondSubPaths = 0L
        allPossibleRolls.forEach {
            val currentPosition = currentPlayer.currentPosition
            val rollHistory = currentPlayer.rollHistory
            val score = currentPlayer.score

            currentPlayer.rollHistory = "${currentPlayer.rollHistory}${it.key}"
            currentPlayer.currentPosition = (currentPlayer.currentPosition + it.key - 1) % 10 + 1
            currentPlayer.score = currentPlayer.score + currentPlayer.currentPosition
            val subPaths = getAllPermutations(playerOne, playerTwo, !playerOneTurn)
            firstSubPaths += subPaths.first
            secondSubPaths += subPaths.second
            currentPlayer.score = score
            currentPlayer.currentPosition = currentPosition
            currentPlayer.rollHistory = rollHistory
        }
        return firstSubPaths to secondSubPaths
    }
}

fun getPermutationsFromHistory(playerOne: String, playerTwo: String): Long {
    return playerOne.playerPermutations() * playerTwo.playerPermutations()
}

fun String.playerPermutations(): Long {
    return this.map { allPossibleRolls[it.digitToInt()]!!.size.toLong() }
        .mult()
}

private val allPossibleRolls: Map<Int, List<Triple<Int, Int, Int>>> = setOf(
    Triple(1, 1, 1), Triple(1, 1, 2), Triple(1, 1, 3),
    Triple(1, 2, 1), Triple(1, 2, 2), Triple(1, 2, 3),
    Triple(1, 3, 1), Triple(1, 3, 2), Triple(1, 3, 3),
    Triple(2, 1, 1), Triple(2, 1, 2), Triple(2, 1, 3),
    Triple(2, 2, 1), Triple(2, 2, 2), Triple(2, 2, 3),
    Triple(2, 3, 1), Triple(2, 3, 2), Triple(2, 3, 3),
    Triple(3, 1, 1), Triple(3, 1, 2), Triple(3, 1, 3),
    Triple(3, 2, 1), Triple(3, 2, 2), Triple(3, 2, 3),
    Triple(3, 3, 1), Triple(3, 3, 2), Triple(3, 3, 3),
).groupBy { it.sum() }

fun Triple<Int, Int, Int>.sum(): Int {
    return first + second + third
}
fun Triple<Int, Int, Int>.str(): String {
    return "$first$second$third"
}

data class Player(
    var currentPosition: Int,
    var score: Int = 0,
    var rollHistory: String = "",
)

fun main() {
    println(partOne())
    println(partTwo())
}
