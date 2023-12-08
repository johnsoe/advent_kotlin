import kotlin.Int
import util.InputParser
import util.math.mult

val inputParser = InputParser("2023/06/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return inputParser.lines().map {
        val split = it.split(" ")
        countWinningRaces(
            time = split.first().toLong(),
            distance = split.last().toLong()
        )
    }.mult()
}

fun partTwo(): Int {
    return inputParser.lines()
        .fold("" to "") { acc, line ->
            val split = line.split(" ")
            acc.first + split.first() to acc.second + split.last()
        }.let { (time, distance) ->
            countWinningRaces(time.toLong(), distance.toLong())
        }
}

fun countWinningRaces(time: Long, distance: Long): Int {
    return (0L..time).count { wait  ->
        (time - wait) * wait > distance
    }
}
