package util

import util.grid.Grid
import util.grid.toGrid
import java.io.File
import java.lang.UnsupportedOperationException

class InputParser (
    private val fileName: String
) {
    companion object {
        private const val INPUT_BASE = "/Users/ralph/gitroot/advent_kotlin/src/main/kotlin/"
    }

    fun lines(): List<String> = File("$INPUT_BASE${fileName}").readLines()
    fun line(): String = lines().first()
    fun linesInt(): List<Int> = lines().map { it.toInt() }
    fun linesLong(): List<Long> = lines().map { it.toLong() }
    fun charGrid(): Grid<Char> = lines()
        .map { it.toCharArray().toList() }
        .toGrid()

    /**
     * Convert all lines to list of type, separated by @param delimiter
     * Current supported types are
     * Int, Long, Double, String
     */
    inline fun <reified T> linesOfType(delimiter: String = " "): List<List<T>> {
        return lines().map { line ->
            line.split(delimiter)
                .map {
                    when(T::class) {
                        Double::class -> it.toDouble()
                        Int::class -> it.toInt()
                        Long::class -> it.toLong()
                        String::class -> it
                        else -> throw UnsupportedOperationException("Current type ${T::class} is not supported")
                    } as T
                }
        }
    }

    /**
     * Split input lines by delimiter and flatten into a single list.
     */
    fun linesBySeparator(delimiter: String = ","): List<String> {
        return lines().flatMap { it.split(delimiter) }
    }

    /**
     * Combine multiple lines into a single list separated by an empty line.
     */
    fun chunk(): List<List<String>> {
        return lines().fold(mutableListOf<MutableList<String>>()) { acc, item ->
            if (item.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.lastOrNull()?.add(item) ?: acc.add(mutableListOf(item))
            }
            acc
        }
    }

    /**
     * Join the chunked list as a single string, defined by the @param separator
     */
    fun chunkAndJoin(separator: String = " "): List<String> {
        return chunk().map { it.joinToString(separator) }
    }

}