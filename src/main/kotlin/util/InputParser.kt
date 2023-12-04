package util

import java.io.File

class InputParser constructor(
    private val fileName: String
) {
    companion object {
        private const val INPUT_BASE = "/Users/ralph/gitroot/advent_kotlin/src/main/kotlin/"
    }

    fun lines(): List<String> = File("$INPUT_BASE${fileName}").readLines()

    fun linesInt(): List<Int> = lines().map { it.toInt() }

    fun linesBySeparator(delimiter: String = ","): List<String> = lines().flatMap { it.split(delimiter) }

    fun linesByChunk(separator: String = " "): List<String> =
        lines().fold(mutableListOf<MutableList<String>>()) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
            }
            acc
        }.map { it.joinToString(separator) }
}