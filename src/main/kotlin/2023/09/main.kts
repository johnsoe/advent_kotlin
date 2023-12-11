import kotlin.Int
import util.InputParser

val inputParser = InputParser("2023/09/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    return inputParser.linesOfLong().sumOf {
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

fun partTwo(): Long {
    return inputParser.linesOfLong().sumOf {
        it.fullyDerive()
            .map { it.first() }
            .reversed()
            .reduce { acc, l -> l - acc }
    }
}
