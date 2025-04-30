package `2024`.`08`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2024/08/input.txt")
val grid = inputParser.charGrid()

fun partOne(): Int {
    val antiNodeSet = mutableSetOf<Int>()
    parseGridIndices { i, j ->
        val antiNodeVec = grid.deltaOfIndices(i, j) + grid.getVector(i)
        grid.indexByCoords(antiNodeVec.x, antiNodeVec.y)?.let {
            antiNodeSet.add(it)
        }
    }
    return antiNodeSet.size
}

fun parseGridIndices(callback: (i: Int, j: Int) -> Unit) {
    grid.asSequence()
        .withIndex()
        .filter { it.value != '.' }
        .groupBy { it.value }
        .map { it.key to it.value.map { it.index } }
        .forEach { (_, indices) ->
            indices.forEach { i ->
                indices.forEach { j ->
                    if (i != j) callback(i, j)
                }
            }
        }
}

fun partTwo(): Int {
    val antiNodeSet = mutableSetOf<Int>()
    parseGridIndices { i, j ->
        val delta = grid.deltaOfIndices(i, j)
        var antiNodeVec = delta + grid.getVector(i)
        var updatedIndex = grid.indexByCoords(antiNodeVec.x, antiNodeVec.y)
        while (updatedIndex != null) {
            antiNodeSet.add(updatedIndex)
            antiNodeVec += delta
            updatedIndex = grid.indexByCoords(antiNodeVec.x, antiNodeVec.y)
        }
        // Add self, since now this is always an anti-node
        antiNodeSet.add(i)
    }
    return antiNodeSet.size
}

fun main() {
    println(partOne())
    println(partTwo())
}
