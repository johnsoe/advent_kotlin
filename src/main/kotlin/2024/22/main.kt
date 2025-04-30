package `2024`.`22`

import kotlin.Int
import util.InputParser
import kotlin.math.pow

val inputParser = InputParser("2024/22/input.txt")



fun partOne(): Long {
    return inputParser.linesLong().sumOf {
        var current = it
        repeat(2000) {
            current = current.nextSecret()
        }
        current
    }
}

fun partTwo(): Long {
    val outer = mutableMapOf<String, Long>()
    inputParser.linesLong().forEach { input ->
        var current = input
        var price = current % 10
        val prices = mutableListOf<Long>()
        val priceChanges = mutableMapOf<String, Long>()
        repeat(2000) {
            current = current.nextSecret()
            val delta = current % 10 - price
            price = current % 10
            prices.addLast(delta)
            if (prices.size > 4) prices.removeFirst()

            val encoding = prices.toTypedArray().encode()
            if (encoding.isNotBlank() && priceChanges[encoding] == null) {
                priceChanges[encoding] = price
            }
        }
        priceChanges.forEach {
            outer.putIfAbsent(it.key, 0)
            outer[it.key] = outer[it.key]!! + it.value
        }
    }
    return outer.values.max()
}

fun Array<Long>.encode(): String {
    if (this.size != 4) return ""
    val pos = this.encode { it > 0 }
    val neg = this.encode { it < 0 }
    return "$pos$neg"
}

fun String.leadingZeroes(count: Int): String {
    var s = this
    while (s.length < count) {
        s = "0$s"
    }
    return s
}

fun Array<Long>.encode(predicate: (Long) -> Boolean): String {
    return this.withIndex().sumOf {
        if (predicate.invoke(it.value)) {
            it.value * (10.0.pow(this.size - 1 - it.index)).toLong()
        } else {
            0
        }
    }.toString().leadingZeroes(4)
}

fun Long.mix(secret: Long) = this.xor(secret)
fun Long.prune() = this % 16777216

fun Long.nextSecret(): Long {
    val stepA = (this * 64).mix(this).prune()
    val stepB = (stepA / 32).mix(stepA).prune()
    return (stepB * 2048).mix(stepB).prune()
}

fun main() {
    println(partOne())
    println(partTwo())
}
