package util

import java.io.File

class InputParser constructor(
    private val fileName: String
) {
    companion object {
        private const val INPUT_BASE = "/Users/ralph/gitroot/advent_kotlin/src/main/kotlin/"
    }

    fun getInputByLine(): List<String> = File("$INPUT_BASE${fileName}").readLines()

    fun getIntInputByLine(): List<Int> = getInputByLine().map { it.toInt() }

    fun getInputBySeparator(delimiter: String = ","): List<String> = getInputByLine().flatMap { it.split(delimiter) }

    fun getInputByChunk(separator: String = " "): List<String> =
        getInputByLine().fold(mutableListOf<MutableList<String>>()) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
            }
            acc
        }.map { it.joinToString(separator) }
}