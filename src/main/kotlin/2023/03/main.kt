package `2023`.`03`

import util.InputParser
import java.awt.Point
import kotlin.Int

val inputParser = InputParser("2023/03/input.txt")



fun partOne(): Int {
    val input = inputParser.lines()
    var sum = 0
    input.forEachIndexed { yIndex, s ->
        var startIndex = 0
        s.forEachIndexed { xIndex, c ->
            if (xIndex >= startIndex) {
                if (c.isDigit()) {
                    startIndex = xIndex
                    while (startIndex < s.length && s[startIndex].isDigit()) {
                        startIndex++
                    }
                    val range = xIndex..startIndex
                    val num = s.substring(range.first until range.last).toInt()

                    val points = mutableListOf<Point>()
                    for (i in range.first -1..range.last) {
                        points.add(Point(i, yIndex - 1))
                        points.add(Point(i, yIndex))
                        points.add(Point(i, yIndex + 1))
                    }
                    val filtered = points.filter {
                        it.x >= 0 && it.x < s.length && it.y >= 0 && it.y < input.size
                    }.map {
                        input[it.y][it.x]
                    }.filter {
                        !it.isDigit() && it != '.'
                    }
                    if (filtered.isNotEmpty()) {
                        sum += num
                    }
                }
            }
        }
    }
    return sum
}

fun partTwo(): Int {
    val input = inputParser.lines()
    val gearLocations = mutableMapOf<Point, MutableList<Int>>()
    input.forEachIndexed { yIndex, s ->
        var startIndex = 0
        s.forEachIndexed { xIndex, c ->
            if (xIndex >= startIndex) {
                if (c.isDigit()) {
                    startIndex = xIndex
                    while (startIndex < s.length && s[startIndex].isDigit()) {
                        startIndex++
                    }
                    val range = xIndex..startIndex
                    val num = s.substring(range.first until range.last).toInt()

                    val points = mutableListOf<Point>()
                    for (i in range.first -1..range.last) {
                        points.add(Point(i, yIndex - 1))
                        points.add(Point(i, yIndex))
                        points.add(Point(i, yIndex + 1))
                    }
                    points.filter {
                        it.x >= 0 && it.x < s.length && it.y >= 0 && it.y < input.size
                    }.map {
                        input[it.y][it.x] to it
                    }.filter {
                        it.first == '*'
                    }.forEach {
                        if (gearLocations.containsKey(it.second)) {
                            gearLocations[it.second]!!.add(num)
                        } else {
                            gearLocations[it.second] = mutableListOf(num)
                        }
                    }
                }
            }
        }
    }

    return gearLocations.filter {
        it.value.size == 2
    }.map { it.value }
        .sumOf {
            it.first() * it.last()
        }
}

fun main() {
    println(partOne())
    println(partTwo())
    
    println("partTwo")
}
