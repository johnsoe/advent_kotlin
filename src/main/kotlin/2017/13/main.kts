import kotlin.Int
import util.InputParser

val inputParser = InputParser("2017/13/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return layers().interceptions(0)
        .sumOf { (depth, range) -> depth * range }
}

private fun layers(): List<Pair<Int, Int>> {
    return inputParser.lines().map {
        val s = it.split(": ")
        s.first().toInt() to s.last().toInt()
    }
}

private fun List<Pair<Int, Int>>.interceptions(offset: Int): List<Pair<Int, Int>> {
    return this.filter { (depth, range) ->
        val cycle = range * 2 - 2
        (depth + offset) % cycle == 0
    }
}

fun partTwo(): Int {
    val layers = layers()
    var offset = 1
    while(layers.interceptions(offset).isNotEmpty()) {
        offset++
    }
    return offset
}
