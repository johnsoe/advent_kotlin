package `2023`.`18`

import util.InputParser
import util.grid.Direction
import java.awt.Point
import java.util.*
import kotlin.Int
import kotlin.collections.LinkedHashSet

private val inputParser = InputParser("2023/18/input.txt")

private fun partOneAgain(): Long {
    val trench = inputParser.lines()
        .map {
            val split = it.split(" ")
            split[0].toDirection() to split[1].toInt()
        }.fold(LinkedHashSet<Point>().apply { this.add(Point(0, 0)) }) { visited, (direction, amt) ->
            val pt = visited.last()
            val delta = when (direction) {
                Direction.Up -> Point(0, -1)
                Direction.Down -> Point(0, 1)
                Direction.Right -> Point(1, 0)
                Direction.Left -> Point(-1, 0)
                else -> Point(0, 0)
            }
            (1..amt).forEach {
                visited.add(pt.plus(delta.times(it)))
            }
            visited
        }
    val trenchLines = LinkedHashSet<TrenchLine>()
    inputParser.lines()
        .map {
            val split = it.split(" ")
            split[0].toDirection() to split[1].toInt()
        }.fold(Point(0, 0)) { acc, (direction, amt) ->
            val line = when (direction) {
                Direction.Up -> TrenchLine.Vertical(acc.y..(acc.y + amt), acc.x) to Point(acc.x, acc.y + amt)
                Direction.Down -> TrenchLine.Vertical((acc.y - amt)..acc.y, acc.x) to Point(acc.x, acc.y - amt)
                Direction.Right -> TrenchLine.Horizontal(acc.x..(acc.x + amt), acc.y) to Point(acc.x + amt, acc.y)
                Direction.Left -> TrenchLine.Horizontal((acc.x - amt)..acc.x, acc.y) to Point(acc.x - amt, acc.y)
                else -> throw IllegalStateException("lmao")
            }
            trenchLines.add(line.first)
            line.second
        }

    trenchLines.filter { it is TrenchLine.Vertical }.minOf { it.coord }
    (trenchLines.filter { it is TrenchLine.Vertical }.minOf { it.coord }..trenchLines.filter { it is TrenchLine.Vertical }.maxOf { it.coord }).forEach { x ->
        (trenchLines.filter { it is TrenchLine.Horizontal }.minOf { it.coord }..trenchLines.filter { it is TrenchLine.Horizontal }.maxOf { it.coord }).forEach { y ->
            print(
                if (Point(x, y) in trench) {
                    '#'
                } else {
                    '.'
                },
            )
        }
        println()
    }
    return 0
}

private fun partOne(): Long {
    val trench = inputParser.lines()
        .map {
            val split = it.split(" ")
            split[0].toDirection() to split[1].toInt()
        }.fold(LinkedHashSet<Point>().apply { this.add(Point(0, 0)) }) { visited, (direction, amt) ->
            val pt = visited.last()
            val delta = when (direction) {
                Direction.Up -> Point(0, -1)
                Direction.Down -> Point(0, 1)
                Direction.Right -> Point(1, 0)
                Direction.Left -> Point(-1, 0)
                else -> Point(0, 0)
            }
            (1..amt).forEach {
                visited.add(pt.plus(delta.times(it)))
            }
            visited
        }

    /**
     * R 461937
     * D 56407
     * R 356671
     * D 863240
     * R 367720
     * D 266681
     * L 577262
     * U 829975
     * L 112010
     * D 829975
     * L 491645
     * U 686074
     * L 5411
     * U 500254
     */

    println(trench.minOf { it.x })
    println(trench.minOf { it.y })
    (trench.minOf { it.y }..trench.maxOf { it.y }).forEach { y ->
        (trench.minOf { it.x }..trench.maxOf { it.x }).forEach { x ->
            if (x == -10 && y == 1) {
                print('O')
            }
            print(
                if (Point(x, y) in trench) {
                    '#'
                } else {
                    '.'
                },
            )
        }
        println()
    }
    val grouped = trench.groupBy { it.y }
    val startY = grouped.keys.first {
        val sort = grouped[it]!!.sortedBy { it.x }
        sort[0].x + 1 < sort[1].x
    }
    val startX = grouped[startY]!!.sortedBy { it.x }.first().x + 1
    println("$startX $startY")

    val visited = mutableSetOf<Point>()
    val toVisit = LinkedList<Point>().apply { add(Point(startX, startY)) }
    var count = 0
    while (toVisit.isNotEmpty()) {
        val check = toVisit.remove()
        visited.add(check)
        Direction.cardinalDirections().forEach {
            val neighbor = check.getDeltaPosition(it)
            if (neighbor !in visited && neighbor !in trench && neighbor !in toVisit) {
                toVisit.add(neighbor)
            }
        }
        count++
        if (count % 100000 == 0) {
            println("${toVisit.size} $count ${visited.size.toLong() + trench.size.toLong()}")
        }
    }

    return visited.size.toLong() + trench.size.toLong()
//    return 0
}

private fun Point.getDeltaPosition(direction: Direction): Point {
    return when (direction) {
        Direction.Up -> Point(0, -1)
        Direction.Down -> Point(0, 1)
        Direction.Right -> Point(1, 0)
        Direction.Left -> Point(-1, 0)
        else -> Point(0, 0)
    }.plus(this)
}

private fun Point.times(mult: Int): Point {
    return Point(this.x * mult, this.y * mult)
}

private fun Point.plus(add: Point): Point {
    return Point(this.x + add.x, this.y + add.y)
}

fun String.toDirection(): Direction {
    return when (this) {
        "R" -> Direction.Right
        "L" -> Direction.Left
        "U" -> Direction.Up
        "D" -> Direction.Down
        else -> throw IllegalStateException("That ain't it")
    }
}

private fun partTwo(): Int {
    return 0
}

private fun main() {
    println(partOneAgain())
    println(partTwo())
}
