import util.InputParser
import kotlin.Int
import kotlin.math.pow

val inputParser = InputParser("2023/04/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return getWinningNumCountByLine().filter {
        it > 0
    }.sumOf {
        2.0.pow((it - 1).toDouble())
    }.toInt()
}

fun partTwo(): Int {
    val counts = getWinningNumCountByLine()
    val cardCount = mutableMapOf<Int, Int>()
    counts.forEachIndexed { index, _ ->
        cardCount[index] = 1
    }
    counts.forEachIndexed { index, matches ->
        val count = cardCount[index]!!
        for (i in (index + 1)..(index+matches)) {
            cardCount[i] = cardCount[i]!! + count
        }
    }
    return cardCount.map { it.value }.sum()
}

fun getWinningNumCountByLine(): List<Int> {
    return inputParser.getInputByLine().map { line ->
        val split = line.substring(10).split(" | ")
        val winningNums = split.first().trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
        val availableNums = split.last().trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
        winningNums.intersect(availableNums).size
    }
}
