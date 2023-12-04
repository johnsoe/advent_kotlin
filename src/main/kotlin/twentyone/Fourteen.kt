package twentyone

import util.LegacyInputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Fourteen : LegacyInputParser<Long>() {
    public override fun getFileName(): String = "twentyone/input/fourteen.txt"

    public override fun partOne(): Long {
        return calculateMaxDifference(10)
    }

    fun calculateMaxDifference(repeatCount: Int): Long {
        val start = getInputByLine().first()
        val stateMap = mutableMapOf<String, Long>()
        start.take(start.length - 1).forEachIndexed { index, c ->
            val pair = "$c${start[index + 1]}"
            stateMap.putOrUpdate(pair, 1)
        }
        val transforms = generateTransforms()
        repeat(repeatCount) {
            val differential = mutableMapOf<String, Long>()
            transforms.forEach {
                val replace = it.first()
                val replaceCount = stateMap[replace] ?: 0
                if (replaceCount != 0L) {
                    differential.putOrUpdate(replace, -replaceCount)
                    differential.putOrUpdate(it[1], replaceCount)
                    differential.putOrUpdate(it.last(), replaceCount)
                }
            }
            differential.forEach { stateMap.putOrUpdate(it.key, it.value) }
        }
        val sums = stateMap.entries.fold(mutableMapOf<Char, Long>()) { acc, pair ->
            pair.key.forEach { acc.putOrUpdate(it, pair.value) }
            acc
        }
        sums[start.first()] = sums[start.first()]!! + 1
        sums[start.last()] = sums[start.last()]!! + 1
        return (sums.maxOf { it.value } - sums.minOf { it.value }) / 2L
    }

    private fun generateTransforms(): List<Array<String>> {
        return getInputByLine().drop(2).map {
            val split = it.split(" -> ")
            arrayOf(
                split.first(),
                "${split.first().first()}${split.last()}",
                "${split.last()}${split.first().last()}",
            )
        }
    }

    public override fun partTwo(): Long {
        return calculateMaxDifference(40)
    }
}

fun <K> MutableMap<K, Long>.putOrUpdate(key: K, difference: Long) {
    this.putIfAbsent(key, 0L)
    this[key] = this.getValue(key) + difference
}

public fun main(): Unit {
    println(Fourteen.partOne())
    println(Fourteen.partTwo())
}
