package `2016`.`03`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/03/input.txt")



fun partOne(): Int {
    return inputParser.lines()
        .map { lengths ->
            lengths.trim().split("\\s+".toRegex())
                .map { it.toInt() }
        }
        .filter { it.isValidTriangle() }
        .size
}

private fun List<Int>.isValidTriangle(): Boolean {
    if (this.size != 3) {
        return false
    }
    val sorted = this.sorted()
    return sorted[0] + sorted[1] > sorted[2]
}

fun partTwo(): Int {
    return inputParser.lines()
        .map { lengths ->
            lengths.trim().split("\\s+".toRegex())
                .map { it.toInt() }
        }
        .chunked(3)
        .map {
            it.mapIndexed { rIndex, row ->
                val triangle = IntArray(3)
                row.forEachIndexed { cIndex, _ ->
                    triangle[cIndex] = it[cIndex][rIndex]
                }
                triangle.toList()
            }
        }
        .flatten()
        .filter { it.isValidTriangle() }
        .size
}

fun main() {
    println(partOne())
    println(partTwo())
}
