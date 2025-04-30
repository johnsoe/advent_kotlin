package `2021`.`08`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2021/08/input.txt")

fun partOne(): Int {
    return parseCodes().map { it.second }.sumOf {
        it.count { output ->
            output.length == 2 || output.length == 3 || output.length == 4 || output.length == 7
        }
    }
}

fun partTwo(): Int {
    // Determine which codes are 1, 4, 7, 8
    // Determine 3 by which 5 length contains number 1
    // Determine 9, length 6 contains the number 4
    // Determine 0, length 6 contains 7 and is not nine
    // Determine 6, last length 6
    // Determine 5, length 5, number 6 contains 5
    // Determine 2, the last length 5.

    return parseCodes().sumOf { codes ->
        val inputCodes = codes.first
        val one = inputCodes.find { it.length == 2 }!!
        val four = inputCodes.find { it.length == 4 }!!
        val seven = inputCodes.find { it.length == 3 }!!
        val eight = inputCodes.find { it.length == 7 }!!
        val three = inputCodes.find { it.length == 5 && stringContains(it, one) }!!
        val nine = inputCodes.find { it.length == 6 && stringContains(it, four) }!!
        val zero = inputCodes.find { it.length == 6 && it != nine && stringContains(it, seven) }!!
        val six = inputCodes.find { it.length == 6 && it != nine && it != zero }!!
        val five = inputCodes.find { it.length == 5 && stringContains(six, it) }!!
        val two = inputCodes.find { it.length == 5 && it != three && it != five }!!

        val digits = listOf(zero, one, two, three, four, five, six, seven, eight, nine)

        codes.second.joinToString("") { output ->
            val match = digits.find { digit ->
                digit.length == output.length && stringContains(digit, output)
            }
            digits.indexOf(match).toString()
        }.toInt()
    }
}

fun parseCodes(): List<Pair<List<String>, List<String>>> {
    return inputParser.lines().map {
        val split = it.split(" | ")
        split.first().split(" ") to split.last().split(" ")
    }
}

fun stringContains(longer: String, shorter: String): Boolean {
    return shorter.toCharArray().all {
        longer.indexOf(it) != -1
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
