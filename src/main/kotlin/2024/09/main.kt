package `2024`.`09`

import util.InputParser
import util.math.isEven
import kotlin.Int

private val inputParser = InputParser("2024/09/input.txt")

private fun partOne(): Long {
    var id = 0
    var space = 0
    val empties = mutableListOf<Int>()
    val occupied = mutableMapOf<Int, Int>()
    inputParser.line()
        .toCharArray()
        .map { it.digitToInt() }
        .forEachIndexed { index, i ->
            val addresses = (space..<space + i)
            if (index.isEven()) {
                addresses.forEach {
                    occupied[it] = id
                }
                id++
            } else {
                empties.addAll(addresses)
            }
            space += i
        }
    val sorted = occupied.keys.sortedDescending()
    sorted.forEach {
        if (empties.isNotEmpty() && it > empties.first()) {
            val slot = empties.removeFirst()
            occupied[slot] = occupied[it]!!
            occupied.remove(it)
        }
    }
    return occupied.keys.sorted().withIndex().sumOf {
        it.index * occupied[it.value]!!.toLong()
    }
}

private fun partTwo(): Long {
    var id = 0
    var space = 0
    val empties = mutableListOf<IntRange>()
    val occupied = mutableMapOf<Int, IntRange>()
    inputParser.line()
        .toCharArray()
        .map { it.digitToInt() }
        .forEachIndexed { index, i ->
            val addresses = (space..<space + i)
            if (index.isEven()) {
                occupied[id] = addresses
                id++
            } else if (addresses.size() > 0) {
                empties.add(addresses)
            }
            space += i
        }
    val sorted = occupied.keys.sortedDescending()
    sorted.forEach {
        val range = occupied[it]!!
        empties.sortedBy { it.first }.firstOrNull { empty ->
            range.size() <= empty.size() && range.first > empty.first
        }?.let { slot ->
            empties.remove(slot)
            val sizeDelta = slot.size() - range.size()
            occupied[it] = IntRange(slot.first, slot.last - sizeDelta)
            if (sizeDelta > 0) {
                empties.add(IntRange(slot.last - sizeDelta + 1, slot.last))
            }
            empties.add(range)
        }
    }
    return occupied.map {
        it.key.toLong() * it.value.sum()
    }.sum()
}

private fun IntRange.size(): Int {
    return this.last - this.first + 1
}

private fun main() {
    println(partOne())
    println(partTwo())
}
