import util.InputParser

val inputParser = InputParser("2023/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return inputParser.getInputByLine()
        .sumOf { sumOfLineWithoutText(it) }
}

fun partTwo(): Int {
    val nums = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    return inputParser.getInputByLine().sumOf { line ->
        val indexMap = mutableMapOf<Int, Int>()
        nums.forEach {
            indexMap[line.indexOf(it.key)] = it.value
            indexMap[line.lastIndexOf(it.key)] = it.value
        }

        var tempLine = line
        indexMap.filter { it.key != -1 }
            .forEach { (index, num) ->
                tempLine = tempLine.substring(0, index) + num + tempLine.substring(index + 1)

            }
        sumOfLineWithoutText(tempLine)
    }
}

fun sumOfLineWithoutText(line: String): Int {
    var tens = line.findFirstDigit() * 10
    tens += line.reversed().findFirstDigit()
    return tens
}

fun String.findFirstDigit(): Int {
    for (c in this) {
        if (c.isDigit()) {
            return c.digitToInt()
        }
    }
    throw IllegalStateException("String needs a digit")
}
