package twentyone

import InputParser

object Three: InputParser<Int>() {
    override fun getFileName() = "three.txt"

    override fun partOne(): Int {
        val binaryInput = getInputByLine().map { it.toInt(2) }
        val max = binaryInput.maxOf { it }
        var flag = 1
        var output = 0 to 0
        while (flag < max) {
            val oneCount = binaryInput.count { it and flag == flag }
            output = if (oneCount > binaryInput.size - oneCount) {
                output.first + flag to output.second
            } else {
                output.first to output.second + flag
            }
            flag *= 2
        }
        return output.multiply()
    }

    override fun partTwo(): Int {
        val binaryInput = getInputByLine().map {
            it.reversed().toInt(2)
        }
        return (getRating(binaryInput, true) to getRating(binaryInput, false)).multiply()
    }

    private fun getRating(input: List<Int>, flip: Boolean): Int {
        val max = input.maxOf { it }
        var flag = 1
        var temp = input
        while (flag < max && temp.size != 1) {
            val oneCount = temp.count { it and flag == flag }
            temp = if (oneCount >= temp.size - oneCount) {
                if (flip) {
                    temp.filter { it and flag != flag }
                } else {
                    temp.filter { it and flag == flag }
                }
            } else {
                if (flip) {
                    temp.filter { it and flag == flag }
                } else {
                    temp.filter { it and flag != flag }
                }
            }
            flag *= 2
        }
        return Integer.toBinaryString(temp.first()).reversed().toInt(2)
    }

    private fun Pair<Int, Int>.multiply(): Int {
        return this.first * this.second
    }
}