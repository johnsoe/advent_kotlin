import java.io.File

abstract class InputParser<T> {
    companion object {
        private const val INPUT_BASE = "/Users/evanjr/Documents/gitroot/advent_2021/out/production/advent_2021/input/"
    }
    abstract fun getFileName(): String
    abstract fun first(): T
    abstract fun second(): T

    fun getInputByLine(): List<String> = File("$INPUT_BASE${getFileName()}").readLines()
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