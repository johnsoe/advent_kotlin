package `2022`.`14`

import util.InputParser
import java.awt.Point
import kotlin.Int
import kotlin.math.max
import kotlin.math.min

private val inputParser = InputParser("2022/14/input.txt")

private fun partOne(): Int {
    val blockedPoints = getStartingPoints()
    val lowestPoint = blockedPoints.map { it.y }.max()
    var sandCount = 0
    while (true) {
        sandCount++
        val emitted = Point(500, 0)
        while (emitted.y <= lowestPoint && !emitted.isFullyBlocked(blockedPoints)) {
            emitted.update(blockedPoints)
        }
        if (emitted.y > lowestPoint) {
            return sandCount - 1
        } else {
            blockedPoints.add(emitted)
        }
    }

    return 0
}

fun getStartingPoints(): MutableSet<Point> {
    val startingPoints = mutableSetOf<Point>()
    inputParser.lines()
        .map {
            it.split(",", " -> ")
                .map { it.toInt() }
                .chunked(2)
                .map { Point(it[0], it[1]) }
        }.map { allPoints ->
            allPoints.dropLast(1).forEachIndexed { index, point ->
                val xRange = min(allPoints[index + 1].x, point.x)..max(allPoints[index + 1].x, point.x)
                val yRange = min(allPoints[index + 1].y, point.y)..max(allPoints[index + 1].y, point.y)

                if (xRange.count() > yRange.count()) {
                    repeat(xRange.count()) {
                        startingPoints.add(Point(xRange.start + it, point.y))
                    }
                } else {
                    repeat(yRange.count()) {
                        startingPoints.add(Point(point.x, yRange.start + it))
                    }
                }
            }
        }
    return startingPoints
}

private fun Point.update(blockedPoints: Set<Point>) {
    when {
        canDrop(blockedPoints) -> {
            this.y = this.y + 1
        }
        canDropLeft(blockedPoints) -> {
            this.y = this.y + 1
            this.x = this.x - 1
        }
        canDropRight(blockedPoints) -> {
            this.y = this.y + 1
            this.x = this.x + 1
        }
    }
}

private fun Point.canDrop(blockedPoints: Set<Point>): Boolean {
    return Point(this.x, this.y + 1) !in blockedPoints
}

private fun Point.canDropLeft(blockedPoints: Set<Point>): Boolean {
    return Point(this.x - 1, this.y + 1) !in blockedPoints // && Point(this.x-1, this.y) !in blockedPoints
}

private fun Point.canDropRight(blockedPoints: Set<Point>): Boolean {
    return Point(this.x + 1, this.y + 1) !in blockedPoints // && Point(this.x + 1, this.y) !in blockedPoints
}

private fun Point.isFullyBlocked(blockedPoints: Set<Point>): Boolean {
    return !this.canDrop(blockedPoints) &&
        !this.canDropLeft(blockedPoints) &&
        !this.canDropRight(blockedPoints)
}

private fun partTwo(): Int {
    val blockedPoints = getStartingPoints()
    val lowestPoint = blockedPoints.map { it.y }.max() + 1
    var sandCount = 0
    while (true) {
        sandCount++
        val emitted = Point(500, 0)
        if (emitted in blockedPoints) {
            return sandCount - 1
        }
        while (emitted.y < lowestPoint && !emitted.isFullyBlocked(blockedPoints)) {
            emitted.update(blockedPoints)
        }
        blockedPoints.add(emitted)
    }

    return 0
}

private fun main() {
    println(partOne())
    println(partTwo())
}
