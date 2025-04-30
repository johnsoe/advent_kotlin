package `2016`.`09`

import util.InputParser

val inputParser = InputParser("2016/09/input.txt")

fun partOne(): Long {
    return inputParser.line()
        .decompressLength { false }
}

fun partTwo(): Long {
    return inputParser.line()
        .decompressLength { it.contains('(') }
}

private fun String.decompressLength(subCheck: (String) -> Boolean): Long {
    var parseIndex = 0
    var sum = 0L
    while (parseIndex < this.length) {
        val check = this[parseIndex]
        if (check == '(') {
            val dropped = this.drop(parseIndex)
            val xIndex = dropped.indexOf('x')
            val markerIndex = dropped.indexOf(')')

            // Pull relevant data from the full string
            val count = this.substring(parseIndex + 1 until parseIndex + xIndex).toInt()
            val repeat = this.substring(parseIndex + xIndex + 1 until parseIndex + markerIndex).toInt()
            val substr = this.substring(parseIndex + markerIndex + 1..parseIndex + markerIndex + count)

            // Conditional check for if we need to recursively decompress
            if (subCheck(substr)) {
                sum += substr.decompressLength(subCheck) * repeat
            } else {
                sum += count * repeat
            }
            parseIndex += markerIndex + count + 1
        } else {
            sum++
            parseIndex++
        }
    }
    return sum
}

fun main() {
    println(partOne())
    println(partTwo())
}
