package `2023`.`11`

import kotlin.Int
import util.InputParser
import util.math.manhattanDistance
import java.awt.Point
import kotlin.math.max
import kotlin.math.min

val inputParser = InputParser("2023/11/input.txt")



fun partOne() = calculateDistances()
fun partTwo() = calculateDistances(gapMultiplier = 999999)

fun calculateDistances(gapMultiplier: Long = 1): Long {
    val lines = inputParser.lines()
    val emptyRows = lines.getEmptyRowIndices()
    val emptyCols = lines.getEmptyColIndices()
    val stars = lines.getStars()

    return stars.mapIndexed { index, point ->
        stars.drop(index).sumOf { other ->
            val rowOverlap = emptyRows.count {
                it in min(point.y, other.y)..max(point.y, other.y)
            } * gapMultiplier
            val colOverlap = emptyCols.count {
                it in min(point.x, other.x)..max(point.x, other.x)
            } * gapMultiplier
            point.manhattanDistance(other) + rowOverlap + colOverlap
        }
    }.sum()
}

fun List<String>.getEmptyRowIndices(): List<Int> {
    return this.withIndex()
        .filter { it.value.all { it == '.' } }
        .map { it.index }
}

fun List<String>.getEmptyColIndices(): List<Int> {
    return (0 until this.first().length).mapIndexed { index, _ ->
        val col = this.map { it[index].toString() }
            .reduce { acc, c -> acc + c }
        if (col.isEmptySpace()) {
            index
        } else {
            null
        }
    }.filterNotNull()
}

fun List<String>.getStars(): List<Point> {
    return this.mapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            if (c == '#') {
                Point(x, y)
            } else {
                null
            }
        }
    }.flatten()
}

fun String.isEmptySpace() = this.all { it == '.'}

fun main() {
    println(partOne())
    println(partTwo())
}
