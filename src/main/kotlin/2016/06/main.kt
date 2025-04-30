package `2016`.`06`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/06/input.txt")



fun partOne(): String {
    return generateCharMaps()
        .map { map ->
            map.keys.sortedByDescending { map[it] }.first()
        }.joinToString("")
}

private fun generateCharMaps(): List<Map<Char, Int>> {
    return inputParser.lines()
        .fold(mutableListOf<MutableMap<Char, Int>>()) { acc, s ->
            s.forEachIndexed { index, c ->
                if (index >= acc.size) {
                    acc.add(mutableMapOf())
                }
                acc[index].putIfAbsent(c, 0)
                acc[index][c] = acc[index][c]!! + 1
            }
            acc
        }
}

fun partTwo(): String {
    return generateCharMaps()
        .map { map ->
            map.keys.sortedBy { map[it] }.first()
        }.joinToString("")
}

fun main() {
    println(partOne())
    println(partTwo())
}
