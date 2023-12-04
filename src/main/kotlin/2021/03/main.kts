import util.InputParser
import kotlin.Int

val inputParser = InputParser("2021/03/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val binaryInput = inputParser.lines().map { it.toInt(2) }
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

fun partTwo(): Int {
    val binaryInput = inputParser.lines().map {
        it.reversed().toInt(2)
    }
    return (getRating(binaryInput, true) to getRating(binaryInput, false)).multiply()
}

fun getRating(input: List<Int>, flip: Boolean): Int {
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

fun Pair<Int, Int>.multiply(): Int {
    return this.first * this.second
}
