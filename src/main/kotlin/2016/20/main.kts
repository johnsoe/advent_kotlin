import kotlin.Int
import util.InputParser
import util.math.isAdjacent
import util.math.union

val inputParser = InputParser("2016/20/input.txt")
println(partOne())
println(partTwo())

// Probably should've sorted input to make this simpler.
fun partOne(): Long {
    val exclusions = generateExclusions()
    return exclusions.dropLast(1).filterIndexed { index, longRange ->
        !longRange.isAdjacent(exclusions[index + 1])
    }.first().last + 1
}

private fun generateExclusions(): List<LongRange> {
    var exclusions = listOf<LongRange>()
    inputParser.lines()
        .map {
            val split = it.split("-")
            LongRange(split[0].toLong(), split[1].toLong())
        }.forEach { inputRange ->
            val result = mutableListOf(inputRange)
            exclusions.forEach { longRange ->
                val union = longRange.union(result.last())
                if (union != null) {
                    result.removeLast()
                    result.addLast(union)
                } else {
                    result.addFirst(longRange)
                }
            }
            exclusions = result.sortedBy { it.first }
        }
    return exclusions
}

fun partTwo(): Long {
    val exclusions = generateExclusions()
    return (exclusions.last().last + 1) - exclusions.sumOf {
        it.last - it.first + 1
    }
}
