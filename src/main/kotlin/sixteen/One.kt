package sixteen

import LegacyInputParser
import util.Direction
import java.lang.IllegalStateException
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.math.abs

public object One : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "sixteen/input/one.txt"

    public override fun partOne(): Int {
        val startDirection: Direction = Direction.Up
        val folded = getInputBySeparator(", ").fold(Triple(startDirection,  0,  0)) { acc, direction ->
            val nextDirection = when(direction.first()) {
                'L' -> acc.first.turnLeft()
                'R' -> acc.first.turnRight()
                else -> Direction.Up
            }
            val vector = direction.drop(1).toInt()
            val diff = when (nextDirection) {
                Direction.Up -> 0 to vector
                Direction.Down -> 0 to -vector
                Direction.Left -> -vector to 0
                Direction.Right -> vector to 0
                else -> throw IllegalStateException()
            }
            Triple(nextDirection, acc.second + diff.first, acc.third +  diff.second)
        }
        return abs(folded.second) + abs(folded.third)
    }

    public override fun partTwo(): Int {
        val startDirection: Direction = Direction.Up
        val locationSet = mutableSetOf<Pair<Int, Int>>().apply { add(0 to 0) }
        val folded = getInputBySeparator(", ").fold(Triple(startDirection,  0,  0)) { acc, direction ->
            val nextDirection = when(direction.first()) {
                'L' -> acc.first.turnLeft()
                'R' -> acc.first.turnRight()
                else -> Direction.Up
            }
            val vector = direction.drop(1).toInt()
            val diff = when (nextDirection) {
                Direction.Up -> 0 to vector
                Direction.Down -> 0 to -vector
                Direction.Left -> -vector to 0
                Direction.Right -> vector to 0
                else -> throw IllegalStateException()
            }
            val nextPosition = acc.second + diff.first to acc.third + diff.second
            val monoDiff = diff.first + diff.second
            val isPositive = monoDiff > 0
            repeat(abs(monoDiff)) {
                val pair = when {
                    diff.first == 0 && isPositive -> acc.second to acc.third + it + 1
                    diff.first == 0 && !isPositive -> acc.second to acc.third - it - 1
                    diff.second == 0 && isPositive -> acc.second + it + 1 to acc.third
                    diff.second == 0 && !isPositive -> acc.second - it - 1 to acc.third
                    else -> throw IllegalStateException()
                }
                if (locationSet.contains(pair)) {
                    return abs(pair.first) + abs(pair.second)
                } else {
                    locationSet.add(pair)
                }
            }
            Triple(nextDirection, nextPosition.first, nextPosition.second)
        }
        return abs(folded.second) + abs(folded.third)
    }
}

public fun main(): Unit {
    println(One.partOne())
    println(One.partTwo())
}
