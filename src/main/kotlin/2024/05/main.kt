package `2024`.`05`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/05/input.txt")



fun partOne(): Int {
    val rules = rules()
    return updates()
        .filter { it.adheresToRules(rules) }
        .sumOf { it[it.size / 2] }
}

private fun rules(): List<Pair<Int, Int>> {
    return inputParser.lines()
        .takeWhile { it.isNotBlank() }
        .map {
            val ruleList = it.split("|")
                .map { it.toInt() }
            ruleList.first() to ruleList.last()
        }
}

private fun updates(): List<List<Int>> {
    return inputParser.lines()
        .takeLastWhile { it.isNotBlank() }
        .map {
            it.split(",")
                .map { it.toInt() }
        }
}

private fun List<Int>.adheresToRules(
    rules: List<Pair<Int, Int>>
): Boolean {
    val prev = mutableListOf<Int>()
    val next = this.toMutableList()
    return this.all { current ->
        next.removeFirst()
        val result = next.all { (current to it) in rules } &&
            prev.all { (current to it) !in rules }
        prev.add(current)
        result
    }
}

fun partTwo(): Int {
    val rules = rules()
    return updates()
        .filter { !it.adheresToRules(rules) }
        .map {
            val order = it.toMutableList()
            while (!order.adheresToRules(rules)) {
                for (i in 0..<order.size) {
                    for (j in i+1..<order.size) {
                        if ((order[i] to order[j]) !in rules) {
                            val temp = order[i]
                            order[i] = order[j]
                            order[j] = temp
                        }
                    }
                }
            }
            order
        }
        .sumOf { it[it.size / 2] }
}

fun main() {
    println(partOne())
    println(partTwo())
}
