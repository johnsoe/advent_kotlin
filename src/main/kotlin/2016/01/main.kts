import sixteen.One
import util.Direction
import util.InputParser
import java.lang.IllegalStateException
import kotlin.Int
import kotlin.math.abs

val inputParser = InputParser("2016/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val startDirection: Direction = Direction.Up
    val folded = inputParser.getInputBySeparator(", ").fold(Triple(startDirection,  0,  0)) { acc, direction ->
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

fun partTwo(): Int {
    val startDirection: Direction = Direction.Up
    val locationSet = mutableSetOf<Pair<Int, Int>>().apply { add(0 to 0) }
    val folded = inputParser.getInputBySeparator(", ").fold(Triple(startDirection,  0,  0)) { acc, direction ->
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
