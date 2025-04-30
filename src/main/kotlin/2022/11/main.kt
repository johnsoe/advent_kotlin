package `2022`.`11`

import util.InputParser
import util.math.mult
import java.util.*
import kotlin.Int

val inputParser = InputParser("2022/11/input.txt")

fun partOne(): Long {
    val monkeys = getMonkeys()
    val monkeyMap = monkeys.associateBy { it.id }
    repeat(20) {
        monkeys.forEach {
            do {
                val inspection = it.inspect()
                inspection?.let { (mId, worry) ->
                    monkeyMap[mId]!!.addItem(worry)
                }
            } while (inspection != null)
        }
    }
    return getInspectionMultiplier(monkeys)
}

fun getMonkeys(): List<Monkey> {
    return inputParser.lines().chunked(7)
        .map {
            val id = it[0][it[0].length - 2].code - 48
            val items = it[1].split(", ", ": ").drop(1).map { it.toLong() }
            val operation = it[2].split(" ").takeLast(2)
            val test = it[3].getLastInt()
            val tResult = it[4].getLastInt()
            val fResult = it[5].getLastInt()

            Monkey(id, LinkedList(items), operation, test, tResult, fResult)
        }
}

fun String.getLastInt(): Int {
    return this.split(" ").takeLast(1)[0].toInt()
}

fun getInspectionMultiplier(monkeys: List<Monkey>): Long {
    return monkeys.map { it.inspectionCount }
        .sortedDescending()
        .take(2)
        .mult()
}

fun partTwo(): Long {
    val monkeys = getMonkeys()
    val monkeyMap = monkeys.associateBy { it.id }
    val divisor = monkeys
        .map { it.divTest }
        .mult()

    repeat(10000) {
        monkeys.forEach {
            do {
                val inspection = it.smartInspect(divisor)
                inspection?.let { (mId, worry) ->
                    monkeyMap[mId]!!.addItem(worry)
                }
            } while (inspection != null)
        }
    }
    return getInspectionMultiplier(monkeys)
}

fun main() {
    println(partOne())
    println(partTwo())
}
