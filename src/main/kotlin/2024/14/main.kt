package `2024`.`14`

import kotlin.Int
import util.InputParser
import util.math.Vector2
import util.math.mult

val inputParser = InputParser("2024/14/input.txt")
val inputRegex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
val width = 101
val height = 103



fun partOne(): Int {
    return vectors().mapBySteps(100)
        .map { it.getQuadrant() }
        .filter { it != -1 }
        .groupBy { it }
        .map { it.value.size }
        .mult()
}

private fun Vector2.getQuadrant(): Int {
    val xMid = width / 2
    val yMid = height / 2
    return when {
        this.x < xMid && this.y < yMid -> 0
        this.x > xMid && this.y < yMid -> 1
        this.x < xMid && this.y > yMid -> 2
        this.x > xMid && this.y > yMid -> 3
        else -> -1
    }
}

private fun vectors(): List<Pair<Vector2, Vector2>> {
    return  inputParser.lines().map {
        val nums = inputRegex.find(it)?.destructured?.toList()?.map { it.toInt() } ?: emptyList()
        Vector2(nums[0], nums[1]) to Vector2(nums[2], nums[3])
    }
}

private fun List<Pair<Vector2, Vector2>>.mapBySteps(steps: Int): List<Vector2> {
    return this.map { (startP, v) ->
        val endP = startP + (v * steps)
        val mod = Vector2(endP.x % width, endP.y % height)
        val offset = Vector2(
            if (mod.x < 0) width else 0,
            if (mod.y < 0) height else 0
        )
        (mod + offset)
    }
}

fun partTwo(): Int {
    val input = vectors()
    var count = 0
    while (true) {
        val result = input.mapBySteps(count).toSet()
        val midSlice = result.filter { it.x < width / 4 || it.x > width / 4 * 3 }
        if (midSlice.size < result.size / 3) {
            printGrid(result, count)
            Thread.sleep(250)
        }
        count++
    }
    return 0
}

fun printGrid(robots: Set<Vector2>, second: Int) {
    println("======   $second   ======")
    repeat(height) { row ->
        println()
        repeat(width) { col ->
            if (robots.contains(Vector2(col, row))) {
                print("#")
            } else {
                print(".")
            }
        }
    }
    println()
    println()
}

fun main() {
    println(partOne())
    println(partTwo())
}
