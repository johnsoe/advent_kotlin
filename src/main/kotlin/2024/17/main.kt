package `2024`.`17`

import kotlin.Int
import util.InputParser
import java.util.*
import kotlin.math.pow

val inputParser = InputParser("2024/17/input.txt")
private val chunks = inputParser.chunk()
// ugly but it works
private val commands = chunks.last()[0].split(": ").last().split(",").map { it.toInt() }



fun partOne(): String {
    val registers = chunks[0].map {
        it.split(": ").last().toLong()
    }.toLongArray()
    return parse(registers).joinToString(",")
}

private fun parse(regs: LongArray): List<Long> {
    var ptr = 0
    val output = mutableListOf<Long>()
    while (ptr < commands.size) {
        val jump = cmd(regs, commands[ptr], commands[ptr + 1], output)
        if (jump != -1) {
            ptr = jump
        } else {
            ptr += 2
        }
    }
    return output
}

private fun cmd(registers: LongArray, op: Int, operand: Int, output: MutableList<Long>): Int {
    val combo = combo(registers, operand)
    var jump = -1
    when (op) {
        0 -> registers[0] = registers[0] / (2.0.pow(combo.toDouble()).toInt())
        1 -> registers[1] = registers[1].xor(operand.toLong())
        2 -> registers[1] = combo % 8L
        3 -> if (registers[0] != 0L) jump = operand
        4 -> registers[1] = registers[1].xor(registers[2])
        5 -> output.add(combo % 8)
        6 -> registers[1] = registers[0] / (2.0.pow(combo.toDouble()).toInt())
        7 -> registers[2] = registers[0] / (2.0.pow(combo.toDouble()).toInt())
    }
    return jump
}

private fun combo(registers: LongArray, num: Int): Long {
    return when {
        num in 0..3 -> num.toLong()
        num in 4..6 -> registers[num - 4]
        else -> throw IllegalStateException("NOT VALID")
    }
}

fun partTwo(): Long {
    val maxPower = 15
    val outputs = LinkedList<Pair<Long, Int>>()
    outputs.add(8.0.pow(maxPower).toLong() to maxPower)
    while(outputs.isNotEmpty()) {
        val (baseIter, curPow) = outputs.removeFirst()
        repeat(8) {
            val iter = baseIter + 8.0.pow(curPow).toLong() * it
            val registers = listOf(iter, 0, 0).toLongArray()
            val output = parse(registers).map { it.toInt() }

            if (output == commands) {
                return iter
            }
            val eval = maxPower - curPow + 1
            if (output.takeLast(eval) == commands.takeLast(eval)) {
                outputs.add(iter to curPow - 1)
            }
        }
    }
    return 0
}

fun main() {
    println(partOne())
    println(partTwo())
}
