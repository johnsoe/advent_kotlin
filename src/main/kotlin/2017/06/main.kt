package `2017`.`06`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/06/input.txt")

private fun partOne(): Int {
    return cycleCount().first
}

private fun cycleCount(): Pair<Int, Int> {
    val banks = banks()
    val state = mutableMapOf<String, Int>()
    var count = 0
    while (!state.contains(banks.contentToString())) {
        state[banks.contentToString()] = count
        banks.redistribute()
        count++
    }
    return count to (state[banks.contentToString()] ?: 0)
}

private fun banks(): IntArray {
    return inputParser
        .linesOfType<Int>(" ", "\t")
        .flatten()
        .toIntArray()
}

private fun IntArray.redistribute() {
    val max = this.max()
    val delta = max / this.size
    val index = this.indexOf(max)
    this[index] = 0

    for (i in this.indices) {
        val offset = if (i < max % this.size) 1 else 0
        this[(index + i + 1) % this.size] += delta + offset
    }
}

private fun partTwo(): Int {
    return cycleCount().let {
        it.first - it.second
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
