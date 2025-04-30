package `2016`.`05`

import util.InputParser
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.Int

private val positionIndex: Int = 5
private val checkStr = "00000"
private val md5 = MessageDigest.getInstance("MD5")

val inputParser = InputParser("2016/05/input.txt")

fun partOne(): String {
    val code = inputParser.line()
    var index = 0
    var password = ""
    while (password.length != 8) {
        val hash = generateHashedString(code, index)
        if (hash.take(positionIndex) == checkStr) {
            password += hash[positionIndex]
        }
        index++
    }

    return password
}

fun generateHashedString(code: String, index: Int): String {
    val byteArray = (code + index).toByteArray(Charsets.UTF_8)
    val bigInt = BigInteger(1, md5.digest(byteArray))
    return String.format("%032x", bigInt)
}

fun partTwo(): String {
    val code = inputParser.line()
    var index = 0
    val password = mutableMapOf<Int, Char>()

    while (password.size != 8) {
        val hash = generateHashedString(code, index)
        if (hash.take(positionIndex) == checkStr) {
            val position = hash[positionIndex].toString().toInt(16)
            if (position < 8 && !password.containsKey(position)) {
                password[position] = hash[positionIndex + 1]
            }
        }
        index++
    }
    return password.toSortedMap().values.joinToString("")
}

fun main() {
    println(partOne())
    println(partTwo())
}
