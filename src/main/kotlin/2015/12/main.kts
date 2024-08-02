import kotlin.Int
import util.InputParser

val inputParser = InputParser("2015/12/input.txt")
val regex = """-?(?:0|[1-9]\d*)""".toRegex()
println(partOne())
println(partTwo())

fun partOne(): Int {
    return regex.findAll(inputParser.line())
        .map { it.value.toInt() }
        .sum()
}

fun partTwo(): Int {
    val input = inputParser.line()
    val nums = regex.findAll(input)
        .associate { it.range.first to it.value.toInt() }
    val reds = "red".toRegex().findAll(input)
        .map { it.range.first }
        .toSet()

    return input.subSum(0, reds, nums).first
}

fun String.subSum(start: Int, reds: Set<Int>, nums: Map<Int, Int>): Pair<Int, Int> {
    val isArray = this[start] == '['
    var ignore = false
    var sum = 0
    var index = start + 1
    while (this[index] != ']' && this[index] != '}') {
        if (index in reds && !isArray) ignore = true
        if (index in nums) sum += nums[index] ?: 0
        if (this[index] == '[' || this[index] == '{'){
            val (subSum, subIndex) = this.subSum(index, reds, nums)
            sum += subSum
            index = subIndex
        }
        index++
    }
    return if (ignore) 0 to index else sum to index
}
