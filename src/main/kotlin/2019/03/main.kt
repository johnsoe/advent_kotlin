package `2019`.`03`

import util.InputParser
import java.awt.Point
import kotlin.Int
import kotlin.math.abs
import kotlin.math.min

val inputParser = InputParser("2019/03/input.txt")

fun partOne(): Int {
    generatePaths().also { (first, second) ->
        return (first intersect second.toSet())
            .minOf { findDistanceFromCenter(it) }
    }
}

private fun generatePaths(): Pair<List<Point>, List<Point>> {
    val paths = inputParser.linesOfType<String>(",")
        .map { getPathData(it) }
    return paths.first() to paths.last()
}

fun partTwo(): Int {
    val paths = generatePaths()
    val intersection = paths.first intersect paths.second.toSet()
    return findMinimumStepDistance(paths.first, paths.second, intersection) + 2
}

private fun findDistanceFromCenter(point: Point): Int {
    return abs(point.x) + abs(point.y)
}

private fun getPathData(path: List<String>): List<Point> {
    val allPoints = mutableListOf<Point>()
    var temp = Point(0, 0)
    path.forEach { instruction ->
        val range = instruction.substring(1).toInt()
        repeat(range) {
            when (instruction[0]) {
                'U' -> temp = Point(temp.x, temp.y + 1)
                'D' -> temp = Point(temp.x, temp.y - 1)
                'L' -> temp = Point(temp.x - 1, temp.y)
                'R' -> temp = Point(temp.x + 1, temp.y)
            }
            allPoints.add(temp)
        }
    }
    return allPoints
}

private fun findMinimumStepDistance(a: List<Point>, b: List<Point>, i: Set<Point>): Int {
    val stepsA: MutableMap<Point, Int> = mutableMapOf()
    val stepsB: MutableMap<Point, Int> = mutableMapOf()

    i.forEach {
        stepsA[it] = min(calculateSteps(a, it), stepsA[it] ?: Int.MAX_VALUE)
        stepsB[it] = min(calculateSteps(b, it), stepsB[it] ?: Int.MAX_VALUE)
    }
    var min = Integer.MAX_VALUE
    i.forEach {
        min = min(min, stepsA[it]!! + stepsB[it]!!)
    }
    return min
}

private fun calculateSteps(points: List<Point>, point: Point): Int {
    var sum = 0
    var prevPoint = Point(0, 0)
    for (i in 0..points.size) {
        if (points[i] == point) {
            break
        } else {
            sum += abs(prevPoint.x - points[i].x) + abs(prevPoint.y - points[i].y)
        }
        prevPoint = points[i]
    }
    return sum
}

fun main() {
    println(partOne())
    println(partTwo())
}
