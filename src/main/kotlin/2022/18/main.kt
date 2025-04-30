package `2022`.`18`

import kotlin.Int
import util.InputParser
import kotlin.math.abs

val inputParser = InputParser("2022/18/input.txt")



fun partOne(): Int {
    val points = inputParser.linesOfType<Long>(",")

    return points.sumOf { outer ->
        6 - points.filter { inner ->
            outer.isNeighbor(inner)
        }.size
    }
}

//0, 0, 0

//1, 0, 0
//0, 1, 0
//0, 0, 1
//0, 0, -1
//0, -1, 0
//-1, 0, 0

// Need to share two dimensions and the 3rd is off by one.
fun List<Long>.isNeighbor(other: List<Long>): Boolean {
    val cDiffs = this.mapIndexed { index, l ->
        abs(other[index] - l)
    }
    return cDiffs.count { it == 0L } == 2 && cDiffs.count { it == 1L } == 1
}

// 3D flood fill algorithm. Bounds defined by max input values.
// Start fill from corner guaranteed to be "outside"
// Calculate volumes.
fun partTwo(): Int {
    val points = inputParser.linesOfType<Long>(",")
    val upperBound = Triple(
        points.maxOf { it[0] + 1 },
        points.maxOf { it[1] + 1 },
        points.maxOf { it[2] + 1 }
    )
    val lowerBound = Triple(
        points.minOf { it[0] - 1 },
        points.minOf { it[1] - 1 },
        points.minOf { it[2] - 1 }
    )
    return 0
}

fun main() {
    println(partOne())
    println(partTwo())
}
