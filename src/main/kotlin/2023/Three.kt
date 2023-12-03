package `2023`

import LegacyInputParser
import java.awt.Point
import kotlin.Int
import kotlin.String

public object Three : LegacyInputParser<Int>() {
    override fun getFileName(): String = "2023/input/three.txt"

    override fun partOne(): Int {
        val input = getInputByLine()
        val validNums = listOf<Int>()
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
                        val num = s.substring(range.first..range.last-1).toInt()

                        val points = mutableListOf<Point>()
                        for (i in range.start-1..range.last) {
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

    override fun partTwo(): Int {
        val input = getInputByLine()
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
                        val num = s.substring(range.first..range.last-1).toInt()

                        val points = mutableListOf<Point>()
                        for (i in range.start-1..range.last) {
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

        println("partTwo")
        return gearLocations.filter {
            it.value.size == 2
        }.map { it.value }
            .sumOf {
                it.first() * it.last()
            }

    }
}

public fun main() {
    println(Three.partOne())
    println(Three.partTwo())
}
