package twentyone

import InputParser
import kotlin.Int
import kotlin.String
import kotlin.math.abs

public object Seven : InputParser<Long>() {
    public override fun getFileName(): String = "seven.txt"

    public override fun partOne(): Long {
        return updateDistances { position, min, index ->
            abs(position - min - index).toLong()
        }
    }

    private fun updateDistances(callback: (Int, Int, Int) -> Long): Long {
        val positions = getInputBySeparator().map { it.toInt() }
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

    public override fun partTwo(): Long {
        return updateDistances { position, min, index ->
            val distance = abs(position - min - index)
            (distance * (distance + 1) / 2).toLong()
        }
    }
}
