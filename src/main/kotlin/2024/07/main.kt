package `2024`.`07`


import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/07/input.txt")
val add: (Long, Long) -> Long = { a, b -> a + b }
val mult: (Long, Long) -> Long = { a, b -> a * b }
val concat: (Long, Long) -> Long = { a, b -> (a.toString() + b.toString()).toLong()}



fun partOne(): Long {
    return inputParser.linesOfType<Long>(": ", " ")
        .mapNotNull { helper(it[1], 2, it, listOf(add, mult)) }
        .sum()
}

private fun helper(
    sum: Long,
    index: Int,
    nums: List<Long>,
    ops: List<(Long, Long) -> Long>,
): Long? {
    return when {
        sum > nums[0] -> null
        index == nums.size -> {
            if (sum == nums[0]) {
                sum
            } else {
                null
            }
        }
        else -> {
            ops.forEach {
                helper(
                    sum = it(sum, nums[index]),
                    index = index + 1,
                    nums = nums,
                    ops = ops
                )?.let { return it }
            }
            null
        }
    }
}

fun partTwo(): Long {
    return inputParser.linesOfType<Long>(": ", " ")
        .mapNotNull { helper(it[1], 2, it, listOf(add, mult, concat)) }
        .sum()
}

fun main() {
    
println(partOne())
    println(partTwo())
}
