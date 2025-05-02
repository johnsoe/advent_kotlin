package `2022`.`05`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2022/05/input.txt")

private fun partOne(): String {
    val stacks = getInputStacks()
    getInputLines()
        .forEach { command ->
            repeat(command[0]) {
                stacks[command[2] - 1].addFirst(stacks[command[1] - 1].removeFirst())
            }
        }

    return stacks.map { it.first() }
        .joinToString("")
}

fun getInputStacks(): MutableList<ArrayDeque<Char>> {
    return inputParser.lines()
        .take(9)
        .map { ArrayDeque(it.toCharArray().toList()) }
        .toMutableList()
}

fun getInputLines(): List<List<Int>> {
    return inputParser.lines()
        .drop(10)
        .map { line ->
            line.split(" ").mapNotNull { it.toIntOrNull() }
        }
}

private fun partTwo(): String {
    val stacks = getInputStacks()
    getInputLines()
        .forEach { command ->
            val list = mutableListOf<Char>()
            repeat(command[0]) {
                list.add(stacks[command[1] - 1].removeFirst())
            }
            repeat(command[0]) {
                stacks[command[2] - 1].addFirst(list.removeLast())
            }
        }

    return stacks.map { it.first() }
        .joinToString("")
}

private fun main() {
    println(partOne())
    println(partTwo())
}
