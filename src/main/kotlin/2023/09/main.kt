package `2023`.`09`

import util.InputParser

private val inputParser = InputParser("2023/09/input.txt")

private fun partOne(): Long {
    return inputParser.linesOfType<Long>().sumOf {
        it.fullyDerive()
            .reversed()
            .reduce { acc, i -> acc + i.last() }
            .sum()
    }
}

fun List<Long>.derive(): List<Long> {
    return this.dropLast(1)
        .mapIndexed { index, l ->
            this[index + 1] - l
        }
}

fun List<Long>.isFullyDerived(): Boolean {
    return this.all { it == 0L }
}

fun List<Long>.fullyDerive(): List<List<Long>> {
    val derived = mutableListOf(this)
    while (!derived.last().isFullyDerived()) {
        derived.add(derived.last().derive())
    }
    return derived
}

private fun partTwo(): Long {
    return inputParser.linesOfType<Long>().sumOf {
        it.fullyDerive()
            .map { it.first() }
            .reversed()
            .reduce { acc, l -> l - acc }
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
