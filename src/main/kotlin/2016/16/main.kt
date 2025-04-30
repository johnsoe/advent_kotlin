package `2016`.`16`

import util.InputParser
import util.math.isEven
import kotlin.Int

val inputParser = InputParser("2016/16/input.txt")

fun partOne(): String {
    return calculateCheckSum(
        getDragonCurve(272),
    )
}

fun getDragonCurve(diskLength: Int): String {
    var result = inputParser.line()
    while (result.length < diskLength) {
        val buffer = StringBuffer()
        result.reversed().forEach {
            buffer.append(
                if (it == '1') '0' else '1',
            )
        }
        result = "${result}0$buffer"
    }
    return result.take(diskLength)
}

fun calculateCheckSum(curve: String): String {
    var checkSum = curve
    while (checkSum.length.isEven()) {
        checkSum = checkSum.chunked(2)
            .map {
                if (it.first() == it.last()) 1 else 0
            }
            .joinToString("")
    }
    return checkSum
}

fun partTwo(): String {
    return calculateCheckSum(
        getDragonCurve(35651584),
    )
}

fun main() {
    println(partOne())
    println(partTwo())
}
