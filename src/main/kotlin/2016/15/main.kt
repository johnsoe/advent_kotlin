package `2016`.`15`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2016/15/input.txt")

private fun partOne(): Int {
    return calculateDiscAlignment(createDiscs())
}

private fun partTwo(): Int {
    return calculateDiscAlignment(
        createDiscs().apply {
            addLast(
                Disc(
                    positions = 11,
                    startPosition = 0,
                    offset = this.size + 1,
                ),
            )
        },
    )
}

private fun createDiscs(): MutableList<Disc> {
    return inputParser.lines().mapIndexed { index, s ->
        val split = s.split(" ")
        Disc(
            positions = split[3].toInt(),
            startPosition = split.last().dropLast(1).toInt(),
            offset = index + 1,
        )
    }.toMutableList()
}

// This only works because all input discs have a prime # of positions
private fun calculateDiscAlignment(discs: List<Disc>): Int {
    var scaling = 1
    var current = 0
    discs.forEach {
        while ((current + it.startPosition + it.offset) % it.positions != 0) {
            current += scaling
        }
        scaling *= it.positions
    }
    return current
}

private data class Disc(
    val positions: Int,
    val startPosition: Int,
    val offset: Int,
)

private fun main() {
    println(partOne())
    println(partTwo())
}
