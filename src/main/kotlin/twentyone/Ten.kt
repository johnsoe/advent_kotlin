package twentyone

import InputParser
import LegacyInputParser
import java.util.*
import kotlin.String

public object Ten : LegacyInputParser<Long>() {
    public override fun getFileName(): String = "ten.txt"

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

    public override fun partOne(): Long {
        return getInputByLine().map { line ->
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

    private fun Char.isComplement(check: Char): Boolean {
        return when (this) {
            '[' -> check == ']'
            '{' -> check == '}'
            '<' -> check == '>'
            '(' -> check == ')'
            else -> false
        }
    }

    private fun Char.isOpen(): Boolean {
        return "[{(<".contains(this)
    }

    public override fun partTwo(): Long {
        val sorted = getInputByLine().map { line ->
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
}
