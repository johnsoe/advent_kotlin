package `2016`.`07`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2016/07/input.txt")

private fun partOne(): Int {
    return inputParser.lines().map {
        it.split("[", "]")
    }.count {
        val hasABBA = it.filterIndexed { index, s -> index % 2 == 0 && s.hasABBA() }.isNotEmpty()
        val noABBA = it.filterIndexed { index, s -> index % 2 != 0 && s.hasABBA() }.isEmpty()
        hasABBA && noABBA
    }
}

private fun partTwo(): Int {
    return inputParser.lines().map {
        it.split("[", "]")
    }.count { line ->
        val superNetBABs = line.filterIndexed { index, _ -> index % 2 == 0 }
            .map { it.getABAs() }
            .fold(mutableSetOf<String>()) { acc, strings ->
                acc.addAll(strings)
                acc
            }.map {
                it.inverseABA()
            }

        line.filterIndexed { index, _ -> index % 2 != 0 }
            .any { hypernet ->
                superNetBABs.any {
                    hypernet.contains(it)
                }
            }
    }
}

private fun String.hasABBA(): Boolean {
    return this.withIndex()
        .any { (index, _) ->
            if (index + 3 >= this.length) {
                false
            } else {
                this.substring(index..index + 3).isABBA()
            }
        }
}

private fun String.isABBA(): Boolean {
    // Must be length 4 and have different first and second characters
    if (this.length != 4 || this[0] == this[1]) {
        return false
    }
    return this.substring(0..1) == this.substring(2..3).reversed()
}

private fun String.getABAs(): Set<String> {
    val abas = mutableSetOf<String>()
    this.dropLast(2)
        .forEachIndexed { index, _ ->
            val sub = this.substring(index..index + 2)
            if (sub.isABA()) {
                abas.add(sub)
            }
        }
    return abas
}

private fun String.isABA(): Boolean {
    // Must be length 3 with the first and third characters matching and the middle character different
    return this.length == 3 && this[0] == this[2] && this[0] != this[1]
}

private fun String.inverseABA(): String {
    if (!this.isABA()) {
        return this
    }
    return "${this[1]}${this[0]}${this[1]}"
}

private fun main() {
    println(partOne())
    println(partTwo())
}
