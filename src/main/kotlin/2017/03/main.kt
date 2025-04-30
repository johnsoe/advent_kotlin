package `2017`.`03`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.math.Vector2
import kotlin.math.abs

val inputParser = InputParser("2017/03/input.txt")



fun partOne(): Int {
    val num = inputParser.line().toInt()
    var ringNum = 1
    while (num > ringNum.ringSquare()) {
        ringNum++
    }
    val sideLength = (ringNum - 1) * 2
    var high = ringNum.ringSquare()
    var low = high - sideLength
    while (low > num)  {
        low -= sideLength
        high -= sideLength
    }
    return ringNum - 1 + abs((high - num) - (num - low)) / 2
}

fun Int.ringSquare(): Int {
    val root = this * 2 - 1
    return root * root
}

fun partTwo(): Int {
    val num = inputParser.line().toInt()
    val grid = mutableMapOf<Vector2, Int>()

    // Too many variables for this
    var dir: Direction = Direction.Right
    var incrDir = 0
    var dirCount = 1
    var shouldUpCount = false
    var current = Vector2(0,0) to 1
    while (num > current.second) {
        grid[current.first] = current.second
        incrDir++
        val nextVec = current.first + dir.toVec()
        current = nextVec to grid.sumNear(nextVec)
        // We need to turn, but may need to step the same amount
        if (incrDir == dirCount) {
            incrDir = 0
            dir = dir.turnLeft()
            if (shouldUpCount) dirCount++
            shouldUpCount = !shouldUpCount
        }
    }
    return current.second
}

fun Map<Vector2, Int>.sumNear(v: Vector2) =
    Direction.allDirections().sumOf {
        this[v + it.toVec()] ?: 0
    }

fun main() {
    println(partOne())
    println(partTwo())
}
