package `2022`.`13`

import util.InputParser
import util.math.mult
import java.util.*
import kotlin.Int
import kotlin.math.min

private val inputParser = InputParser("2022/13/input.txt")

private fun partOne(): Int {
    return inputParser.lines()
        .filter { it.isNotBlank() }
        .map { it.toPacket() }
        .chunked(2)
        .mapIndexed { index, pair ->
            if ((pair[0] to pair[1]).isValidOrder() == true) {
                index + 1
            } else {
                0
            }
        }
        .sum()
}

private fun Pair<List<Any>, List<Any>>.isValidOrder(): Boolean? {
    val a = this.first
    val b = this.second

    val length = min(a.size, b.size)

    repeat(length) {
        val aType = a[it]
        val bType = b[it]

        val result = when {
            aType is Int && bType is Int -> {
                if (aType != bType) {
                    aType < bType
                } else {
                    null
                }
            }
            aType !is Int && bType !is Int -> {
                ((aType as List<Any>) to (bType as List<Any>)).isValidOrder()
            }
            aType is Int -> {
                (mutableListOf<Any>(aType) to bType as List<Any>).isValidOrder()
            }
            else -> {
                (aType as List<Any> to mutableListOf(bType)).isValidOrder()
            }
        }
        result?.let { return it }
    }
    return if (b.size == a.size) {
        null
    } else {
        b.size >= a.size
    }
}

// Likely more efficient to parse while traversing instead of converting first.
fun String.toPacket(): List<Any> {
    val packet = mutableListOf<Any>()
    val listStack = Stack<MutableList<Any>>()
    listStack.push(packet)

    var index = 1
    while (index < this.length) {
        val currentList = listStack.peek()
        when (this[index]) {
            ',' -> {}
            '[' -> {
                val newList = mutableListOf<Any>()
                currentList.add(newList)
                listStack.push(newList)
            }
            ']' -> listStack.pop()
            else -> {
                // Need to account for double digit numbers
                var total = 0
                while (this[index].isDigit()) {
                    total *= 10
                    total += this[index].code - 48
                    index++
                }
                currentList.add(total)
                index--
            }
        }
        index++
    }
    return packet
}

private fun partTwo(): Int {
    val dividers = listOf("[[2]]", "[[6]]")
    return inputParser.lines()
        .toMutableList()
        .apply { this.addAll(dividers) }
        .filter { it.isNotBlank() }
        .map { it.toPacket() }
        .sortedWith { o1, o2 ->
            if ((o1 to o2).isValidOrder() == true) {
                -1
            } else {
                1
            }
        }
        .mapIndexed { index, it -> it.toString() to (index + 1) }
        .filter { (str, _) -> str in dividers }
        .map { it.second }
        .mult()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
