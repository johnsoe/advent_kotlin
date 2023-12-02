import twentythree.Two

object One : InputParser<Int>() {
    override fun getFileName() = "twentythree/input/one.txt"

    override fun partOne(): Int {
        var sum = 0
        for (line in getInputByLine()) {
            sum += sumOfLineWithoutText(line)
        }
        return sum
    }

    override fun partTwo(): Int {
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

        var sum = 0
        for (line in getInputByLine()) {
            var tempLine = line
            val mapped = nums.map { tempLine.indexOf(it.key) to it.value}
                .filter { it.first != -1 }
            val reverseMapped = nums.map { tempLine.lastIndexOf(it.key) to it.value }
                .filter { it.first != -1 }
            for (num in mapped) {
                tempLine = tempLine.substring(0, num.first) + num.second + tempLine.substring(num.first + 1)
            }
            for (num in reverseMapped) {
                tempLine = tempLine.substring(0, num.first) + num.second + tempLine.substring(num.first + 1)
            }
            sum += sumOfLineWithoutText(tempLine)
        }
        return sum
    }

    private fun sumOfLineWithoutText(line: String): Int {
        var tens = 0
        for (digit in line) {
            if (digit.isDigit()) {
                tens = digit.digitToInt() * 10
                break
            }
        }

        for (chat in line.reversed()) {
            if (chat.isDigit()) {
                tens += chat.digitToInt()
                break
            }
        }
        return tens
    }
}

public fun main(): Unit {
    println(One.partOne())
    println(One.partTwo())
}
