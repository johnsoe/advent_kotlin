package util

import java.io.File

class InputParser (
    private val fileName: String
) {
    companion object {
        private const val INPUT_BASE = "/Users/ralph/gitroot/advent_kotlin/src/main/kotlin/"
    }

    fun lines(): List<String> = File("$INPUT_BASE${fileName}").readLines()

    fun linesInt(): List<Int> = lines().map { it.toInt() }

    fun linesOfLong(delimiter: String = " "): List<List<Long>> = lines().map {
        it.split(delimiter)
            .map { it.toLong() }
    }

    fun linesBySeparator(delimiter: String = ","): List<String> = lines().flatMap { it.split(delimiter) }

    fun chunk(): List<List<String>> =
        lines().fold(mutableListOf<MutableList<String>>()) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
            }
            acc
        }

    fun chunkAndJoin(separator: String = " "): List<String> =
        lines().fold(mutableListOf<MutableList<String>>()) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
            }
            acc
        }.map { it.joinToString(separator) }
}