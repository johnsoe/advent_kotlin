import util.InputParser
import util.math.manhattanDistance
import java.awt.Point
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val inputParser = InputParser("2022/15/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    val row = 2000000L
    return getInstruments()
        .mapToRangesByRow(row)
        .consolidate()
        .sumOf { it.last - it.first }
}

fun getInstruments(): List<Pair<Point, Point>> {
    return inputParser.lines()
        .map { it.split( " x=", ", y=", ": ")}
        .map { Point(it[1].toInt(), it[2].toInt()) to Point(it[4].toInt(), it[5].toInt())}
}

fun List<Pair<Point, Point>>.mapToRangesByRow(row: Long): List<LongRange> {
    return this.mapNotNull { (sensor, beacon) ->
        val range = sensor.manhattanDistance(beacon)
        val deltaY = abs(sensor.y - row)
        if (deltaY > range) {
            null
        } else {
            val deltaX = range - deltaY
            (sensor.x - deltaX)..(sensor.x + deltaX)
        }
    }
}

fun List<LongRange>.consolidate(): List<LongRange> {
    return this.sortedBy { it.first }
        .fold(mutableListOf()) { acc, r ->
            acc.lastOrNull()?.let { last ->
                if (last.last < r.first) {
                    acc.add(r)
                } else {
                    acc.removeLast()
                    acc.add(min(last.first, r.first)..max(r.last, last.last))
                }
            } ?: acc.add(r)
            acc
        }
}

fun partTwo(): Long {
    val instruments = getInstruments()
    val sensorRange = 0..4000000L

    return sensorRange.firstNotNullOf { row ->
        instruments.mapToRangesByRow(row)
            .consolidate()
            .filter { it.last + 1 in sensorRange }
            .map { (it.last + 1) * sensorRange.last + row }
            .firstOrNull()
    }
}
