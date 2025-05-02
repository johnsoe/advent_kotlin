package `2015`.`04`

import util.InputParser
import java.security.MessageDigest
import kotlin.Int

private val inputParser = InputParser("2015/04/input.txt")
val md = MessageDigest.getInstance("MD5")

private fun partOne() = findLeadingZeroes(5)
private fun partTwo() = findLeadingZeroes(6)

fun findLeadingZeroes(zeroCount: Int): Int {
    val input = inputParser.line()
    var count = 0
    do {
        count++
        val key = "$input$count"
        val hash = key.toHash()
    } while (!hash.hasLeadingZeroes(zeroCount))
    return count
}

fun String.toHash(): String {
    val result = md.digest(this.toByteArray())
    return result.joinToString("") { "%02x".format(it) }
}

fun String.hasLeadingZeroes(zeroCount: Int): Boolean {
    return this.take(zeroCount) == "0".repeat(zeroCount)
}

private fun main() {
    println(partOne())
    println(partTwo())
}
