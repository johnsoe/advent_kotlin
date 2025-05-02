package `2023`.`12`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2023/12/input.txt")
val visited = mutableMapOf<String, Long>()

private fun partOne(): Long {
    return inputParser.lines().map {
        val split = it.split(" ", ",")
        split[0] to split.drop(1).map { it.toInt() }
    }.sumOf {
        visited.clear()
        helper(
            input = it.first,
            index = 0,
            groups = it.second,
            validCount = 0L,
            forward = "",
            dropped = mutableListOf(),
        )
    }
}

// Disgusting recursive helper function that I do not want to revisit to clean up
fun helper(
    input: String,
    index: Int,
    groups: List<Int>,
    validCount: Long,
    forward: String,
    dropped: MutableList<Int>,
): Long {
    if (!forward.isValid(input)) {
        return validCount
    }
    visited[(index to dropped.toList()).convert()]?.let {
        return validCount + it
    }
    if (groups.isEmpty() && (index >= input.length || !input.substring(index).contains('#'))) {
        val result = if (forward.isValid(input)) {
            validCount + 1L
        } else {
            validCount
        }
        return result
    } else if (groups.isEmpty()) {
        return validCount
    } else if (index >= input.length || index + groups.first() > input.length) {
        return validCount
    } else {
        val nextGroup = groups.first()
        val slice = input.substring(index until nextGroup + index)
        val mapped = slice.replace("?", "#")
        val subResult = if (slice.canFit(input, index)) {
            dropped.add(groups.first())
            val sub = visitSubSection(input, index + nextGroup + 1, groups.drop(1), validCount, "$forward$mapped.", dropped)
            dropped.removeLast()

            sub + visitSubSection(input, index + 1, groups, validCount, "$forward.", dropped)
        } else {
            visitSubSection(input, index + 1, groups, validCount, "$forward.", dropped)
            visitSubSection(input, index + 1, groups, validCount, "$forward.", dropped)
        }
        return subResult
    }
}

fun visitSubSection(
    input: String,
    index: Int,
    groups: List<Int>,
    validCount: Long,
    forward: String,
    dropped: MutableList<Int>,
): Long {
    val result = helper(input, index, groups, validCount, forward, dropped)
    visited[(index to dropped.toList()).convert()] = result
    return result
}

fun String.isValid(input: String): Boolean {
    return input.mapIndexedNotNull { index, c ->
        if (c == '#') {
            index
        } else {
            null
        }
    }.all { it >= this.length || this[it] == '#' }
}

fun String.canFit(input: String, index: Int): Boolean {
    return !this.contains(".") &&
        (index + this.length == input.length || input[index + this.length] != '#') &&
        (index - 1 < 0 || input[index - 1] != '#')
}

private fun partTwo(): Long {
    return inputParser.lines().map {
        val split = it.split(" ", ",")
        split[0] to split.drop(1).map { it.toInt() }
    }.map {
        // YUCK
        val input = "${it.first}?${it.first}?${it.first}?${it.first}?${it.first}"
        val counts = it.second + it.second + it.second + it.second + it.second
        input to counts
    }.sumOf {
        visited.clear()
        helper(it.first, 0, it.second, 0L, "", mutableListOf())
    }
}

private fun Pair<Int, List<Int>>.convert(): String {
    return "${this.first},${this.second.joinToString(",")}"
}

private fun main() {
    println(partOne())
    println(partTwo())
}
