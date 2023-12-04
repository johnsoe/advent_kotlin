import util.InputParser
import java.util.*

val inputParser = InputParser("2021/10/input.txt")
println(partOne())
println(partTwo())

private val pointMap = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

private val pointMapTwo = mapOf(
    '(' to 1,
    '[' to 2,
    '{' to 3,
    '<' to 4
)

fun partOne(): Long {
    return inputParser.lines().map { line ->
        val stack = Stack<Char>()
        line.toCharArray().forEach {
            if (it.isOpen()) {
                stack.add(it)
            } else {
                val check = stack.pop()
                if (!check.isComplement(it)) {
                    return@map pointMap[it] ?: 0
                }
            }
        }
        0
    }.sum().toLong()
}

fun Char.isComplement(check: Char): Boolean {
    return when (this) {
        '[' -> check == ']'
        '{' -> check == '}'
        '<' -> check == '>'
        '(' -> check == ')'
        else -> false
    }
}

fun Char.isOpen(): Boolean {
    return "[{(<".contains(this)
}

fun partTwo(): Long {
    val sorted = inputParser.lines().map { line ->
        val stack = Stack<Char>()
        line.toCharArray().forEach {
            if (it.isOpen()) {
                stack.add(it)
            } else {
                val check = stack.pop()
                if (!check.isComplement(it)) {
                    return@map 0
                }
            }
        }
        // TODO: Unsure why `fold` doesn't evaluate stack in pop order
        stack.reversed().fold(0L) { acc, c ->
            acc * 5L + (pointMapTwo[c]?.toLong() ?: 0L)
        }
    }.filter { it != 0L }.sorted()

    return sorted[(sorted.size / 2)]
}
