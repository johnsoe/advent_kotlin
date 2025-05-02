package `2017`.`04`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2017/04/input.txt")

private fun partOne(): Int {
    return inputParser.lines()
        .count {
            val words = it.split(" ")
            words.size == words.toSet().size
        }
}

private fun partTwo(): Int {
    return inputParser.lines()
        .count {
            val words = it.split(" ")
            val counts = words.map { word ->
                val countArray = IntArray(26)
                word.forEach { countArray[it.code - 97]++ }
                countArray
            }

            for (i in counts.indices) {
                for (j in counts.indices) {
                    if (i != j) {
                        if (counts[i].isAnagram(counts[j])) {
                            return@count false
                        }
                    }
                }
            }
            true
        }
}

private fun IntArray.isAnagram(other: IntArray): Boolean {
    return this.withIndex().all {
        it.value == other[it.index]
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
