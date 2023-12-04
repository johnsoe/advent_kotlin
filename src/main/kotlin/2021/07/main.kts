import util.InputParser
import kotlin.Int
import kotlin.math.abs

val inputParser = InputParser("2021/07/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    return updateDistances { position, min, index ->
        abs(position - min - index).toLong()
    }
}

fun updateDistances(callback: (Int, Int, Int) -> Long): Long {
    val positions = inputParser.linesBySeparator().map { it.toInt() }
    val min = positions.minOrNull() ?: 0
    val max = positions.maxOrNull() ?: 0

    val distanceSums = LongArray(max - min)
    positions.forEach { position ->
        distanceSums.forEachIndexed { index, _ ->
            distanceSums[index] += callback(position, min, index)
        }
    }
    return distanceSums.minOrNull() ?: -1L
}

fun partTwo(): Long {
    return updateDistances { position, min, index ->
        val distance = abs(position - min - index)
        (distance * (distance + 1) / 2).toLong()
    }
}
