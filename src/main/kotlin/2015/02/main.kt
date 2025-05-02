package `2015`.`02`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2015/02/input.txt")

private fun partOne(): Int {
    return createDimensions().sumOf {
        val (l, w, h) = it.take(3)
        val sideA = l * w
        val sideB = h * w
        val sideC = h * l

        val surfaceArea = (sideA + sideB + sideC) * 2
        val slack = listOf(sideA, sideB, sideC).min()
        surfaceArea + slack
    }
}

fun createDimensions(): List<List<Int>> {
    return inputParser.lines().map {
        it.split("x")
            .map { it.toInt() }
    }
}

private fun partTwo(): Int {
    return createDimensions().sumOf {
        val dims = it.take(3)
        val bow = dims.fold(1) { acc, i -> acc * i }

        val minArea = dims.sorted()
            .take(2)
            .map { it * 2 }
            .sum()

        minArea + bow
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
