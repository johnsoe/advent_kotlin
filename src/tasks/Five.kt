package tasks

import InputParser
import java.awt.Point
import java.lang.Integer.max
import java.lang.Integer.min

object Five : InputParser<Int>() {
    override fun getFileName() = "five.txt"

    override fun first(): Int {
        return getOverlapCount(
            convertToLines().filter { it.isStraight() }
        )
    }

    private fun getOverlapCount(lines: List<Line>): Int {
        return lines.map { it.getPointSet() }
            .fold(mutableMapOf<Point, Int>()) { acc, points ->
                points.forEach {
                    acc[it] = acc.getOrPut(it) { 0 } + 1
                }
                acc
            }
            .count { it.value > 1 }
    }

    override fun second(): Int {
        return getOverlapCount(convertToLines())
    }

    private fun convertToLines(): List<Line> {
        return getInputByLine().map { line ->
            val split = line.split("(,| -> )".toRegex()).map { it.toInt() }
            Line(
                Point(split[0], split[1]),
                Point(split[2], split[3])
            )
        }
    }

    private data class Line(
        val start: Point,
        val end: Point
    ) {

        fun isStraight() = isVertical() || isHorizontal()
        private fun isVertical() = start.x == end.x
        private fun isHorizontal() = start.y == end.y

        fun getPointSet() : Set<Point> {
            return when {
                isHorizontal() -> {
                    (min(start.x, end.x)..max(start.x, end.x)).map {
                        Point(it, start.y)
                    }
                }
                isVertical() -> {
                    (min(start.y, end.y)..max(start.y, end.y)).map {
                        Point(start.x, it)
                    }
                }
                else -> {
                    val leftPoint = if (start.x < end.x) start else end
                    val rightPoint = if (start.x < end.x) end else start
                    val yRangeReversed = leftPoint.y > rightPoint.y
                    val xRange = leftPoint.x .. rightPoint.x
                    xRange.mapIndexed { index, i ->
                        val y = if (yRangeReversed) -index else index
                        Point(i, y + leftPoint.y)
                    }
                }
            }.toSet()
        }

        override fun toString(): String {
            return "$start $end"
        }
    }
}