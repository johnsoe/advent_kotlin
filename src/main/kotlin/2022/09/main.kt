package `2022`.`09`

import util.InputParser
import java.awt.Point
import kotlin.Int
import kotlin.math.abs

val inputParser = InputParser("2022/09/input.txt")

fun partOne(): Int {
    val headPos = Point(0, 0)
    val tailPos = Point(0, 0)
    val visitedLocations = mutableSetOf<Point>()

    inputParser.lines()
        .map { it[0] to it.split(" ").last().toInt() }
        .forEach { (cmd, count) ->
            repeat(count) {
                headPos.moveByCmd(cmd)
                tailPos.follow(headPos)
                visitedLocations.add(Point(tailPos.x, tailPos.y))
            }
        }
    return visitedLocations.size
}

fun Point.moveByCmd(cmd: Char) {
    when (cmd) {
        'U' -> this.y += -1
        'D' -> this.y += 1
        'L' -> this.x += -1
        'R' -> this.x += 1
    }
}

fun Point.follow(other: Point) {
    val xD = other.x - this.x
    val yD = other.y - this.y

    // Do nothing, we are close enough
    if (abs(xD) < 2 && abs(yD) < 2) {
        return
    }

    when {
        // Diagonal
        xD != 0 && yD != 0 -> {
            this.x = other.x - (xD / 2)
            this.y = other.y - (yD / 2)
        }
        // Horizontal
        xD != 0 -> {
            this.x = other.x - (xD / 2)
        }
        // Vertical
        else -> {
            this.y = other.y - (yD / 2)
        }
    }
}

fun partTwo(): Int {
    val knots = (0..9).map { Point(0, 0) }.toList()
    val endKnot = knots.last()
    val visitedLocations = mutableSetOf<Point>()

    inputParser.lines()
        .map { it[0] to it.split(" ").last().toInt() }
        .forEach { (cmd, count) ->
            repeat(count) {
                knots[0].moveByCmd(cmd)
                knots.drop(1)
                    .forEachIndexed { i, point ->
                        point.follow(knots[i])
                    }
                visitedLocations.add(Point(endKnot.x, endKnot.y))
            }
        }
    return visitedLocations.size
}

fun main() {
    println(partOne())
    println(partTwo())
}
