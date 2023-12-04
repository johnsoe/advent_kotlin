import util.InputParser
import java.awt.Point
import kotlin.Int

val inputParser = InputParser("2021/05/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return getOverlapCount(
        convertToLines().filter { it.isStraight() }
    )
}

fun getOverlapCount(lines: List<Line>): Int {
    return lines.map { it.getPointSet() }
        .fold(mutableMapOf<Point, Int>()) { acc, points ->
            points.forEach {
                acc[it] = acc.getOrPut(it) { 0 } + 1
            }
            acc
        }
        .count { it.value > 1 }
}

fun partTwo(): Int {
    return getOverlapCount(convertToLines())
}

fun convertToLines(): List<Line> {
    return inputParser.lines().map { line ->
        val split = line.split("(,| -> )".toRegex()).map { it.toInt() }
        Line(
            Point(split[0], split[1]),
            Point(split[2], split[3])
        )
    }
}

data class Line(
    val start: Point,
    val end: Point
) {

    fun isStraight() = isVertical() || isHorizontal()
    private fun isVertical() = start.x == end.x
    private fun isHorizontal() = start.y == end.y

    fun getPointSet() : Set<Point> {
        return when {
            isHorizontal() -> {
                (Integer.min(start.x, end.x)..Integer.max(start.x, end.x)).map {
                    Point(it, start.y)
                }
            }
            isVertical() -> {
                (Integer.min(start.y, end.y)..Integer.max(start.y, end.y)).map {
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
