import kotlin.Int
import util.InputParser

val inputParser = InputParser("2015/17/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val sizes = getSortedSizes().toMutableList()
    return sizeCombinations(sizes, 150, mutableSetOf()).size
}

fun sizeCombinations(
    sizes: MutableList<IndexedValue<Int>>,
    remaining: Int,
    cache: MutableSet<List<IndexedValue<Int>>> // yuck
): MutableList<Int> {
    val list = sizes.toList()
    if (list !in cache) {
        cache.add(list)
    }
    return if (remaining == 0) {
        mutableListOf(sizes.size)
    } else {
        val count = mutableListOf<Int>()
        list.forEachIndexed { index, num ->
            sizes.removeAt(index)
            if (sizes !in cache && remaining - num.value >= 0) {
                count += sizeCombinations(sizes, remaining - num.value, cache)
            }
            sizes.add(index, num)
        }
        count
    }
}

fun getSortedSizes(): List<IndexedValue<Int>> {
    return inputParser.linesInt()
        .sortedDescending()
        .withIndex()
        .toList()
}

fun partTwo(): Int {
    val sizes = getSortedSizes().toMutableList()
    val result = sizeCombinations(sizes, 150, mutableSetOf())
    val max = result.max()
    return result.count { it == max }
}
