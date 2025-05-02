package `2016`.`14`

import util.InputParser
import java.security.MessageDigest
import kotlin.Int

private val inputParser = InputParser("2016/14/input.txt")
val md = MessageDigest.getInstance("MD5")

private fun partOne(): Int {
    return findKeysWithHashRepeatCount(1)
}

private fun partTwo(): Int {
    return findKeysWithHashRepeatCount(2017)
}

private fun findKeysWithHashRepeatCount(repeatCount: Int = 0): Int {
    val triples = initCharMap()
    val input = inputParser.line()
    val keys = mutableSetOf<Int>()
    var index = 0

    fun analyzeHash() {
        val hexString = "$input$index".toStretchedHash(repeatCount)
        hexString.getRepeatedChar(5)?.let {
            val result = triples[it]?.filter { tIndex ->
                tIndex > index - 1000
            } ?: emptyList()
            keys.addAll(result)
        }
        hexString.getRepeatedChar(3)?.let { triples[it]?.add(index) }
        index++
    }

    while (keys.size < 64) {
        analyzeHash()
    }
    repeat(1000) {
        analyzeHash()
    }
    return keys.sorted().take(64).last()
}

fun String.toStretchedHash(count: Int): String {
    var hash = this
    repeat(count) {
        val result = md.digest(hash.toByteArray())
        hash = result.joinToString("") { "%02x".format(it) }
    }
    return hash
}

private fun String.getRepeatedChar(c: Int): Char? {
    var count = 1
    var prev = this.first()
    this.drop(1).forEach {
        if (it == prev) {
            count++
            if (count == c) return it
        } else {
            count = 1
        }
        prev = it
    }
    return null
}

private fun initCharMap(): MutableMap<Char, MutableSet<Int>> {
    return mutableMapOf<Char, MutableSet<Int>>().apply {
        repeat(16) {
            this[it.toString(16).first()] = mutableSetOf()
        }
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
