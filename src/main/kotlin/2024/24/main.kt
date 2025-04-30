package `2024`.`24`

import util.InputParser
import java.util.*
import kotlin.Int

val gateRegex = """(.*) (OR|XOR|AND) (.*) -> (.*)""".toRegex()
val stateRegex = """(.*): (\d)""".toRegex()
val inputParser = InputParser("2024/24/input.txt")

fun partOne(): Long {
    val state = mutableMapOf<String, Int>()
    val gates = inputParser.chunk().last().map {
        val input = gateRegex.find(it)?.destructured?.toList() ?: emptyList()
        Gate(input[1], listOf(input[0], input[2]), input[3])
    }.toSet()
    inputParser.chunk().first().forEach {
        val input = stateRegex.find(it)?.destructured?.toList() ?: emptyList()
        state[input[0]] = input[1].toInt()
    }

    return calculate(gates, state)
}

private fun calculate(gates: Set<Gate>, state: MutableMap<String, Int>): Long {
    val visited = mutableSetOf<Gate>()
    val queue = LinkedList<Gate>()
    queue.addAll(gates.filter { it.inputs.all { it in state.keys } })
    visited.addAll(queue)
    while (queue.isNotEmpty()) {
        val gate = queue.removeFirst()
        val inA = state[gate.inputs[0]] ?: 0
        val inB = state[gate.inputs[1]] ?: 0
        when (gate.op) {
            "XOR" -> state[gate.output] = inA.xor(inB)
            "OR" -> state[gate.output] = inA.or(inB)
            "AND" -> state[gate.output] = inA.and(inB)
        }
        val updatedGates = gates.filter { it !in visited && it.inputs.all { it in state.keys } }
        queue.addAll(updatedGates)
        visited.addAll(updatedGates)
    }
    val result = getStateByPrefix(state, "z")
    return result.toLong(radix = 2)
}

private data class Gate(
    val op: String,
    val inputs: List<String>,
    val output: String,
) {
    fun gateByInput(a: String, b: String? = null, op: String): Boolean {
        val check = a in inputs && this.op == op
        return if (b != null) {
            check && b in inputs
        } else {
            check
        }
    }
}

// Part two was done by finding areas of the adder where there was an error
// and then manually drawing the circuit diagram to understand the mistake.
// The hard coded values below were discovered in this manner and verified by swapping
// input. Some of the ugliest code I've written, yet it helped me solve this problem.
fun partTwo(): String {
    val state = mutableMapOf<String, Int>()
    val gates = inputParser.chunk().last().map {
        val input = gateRegex.find(it)?.destructured?.toList() ?: emptyList()
        Gate(input[1], listOf(input[0], input[2]), input[3])
    }.toSet()
    inputParser.chunk().first().forEach {
        val input = stateRegex.find(it)?.destructured?.toList() ?: emptyList()
        state[input[0]] = input[1].toInt()
    }
    val xStr = getStateByPrefix(state, "x")
    val yStr = getStateByPrefix(state, "y")
    val expectedZSum = (xStr.toLong(radix = 2) + yStr.toLong(radix = 2)).toString(2)
    val actualZSum = calculate(gates, state).toString(2)

    println("e $expectedZSum")
    println("a $actualZSum")

    for (i in 3..<xStr.length) {
        val xI = i.indexStr('x')
        val yI = i.indexStr('y')
        val zO = i.indexStr('z')

        val andGate = gates.find { it.gateByInput(xI, yI, "AND") }
        if (andGate == null) println("No AND Gate $i")

        val xorGate = gates.find { it.gateByInput(xI, yI, "XOR") }?.let { outXor ->

            val out = outXor.output
            gates.find { it.gateByInput(out, op = "XOR") }?.let {
                if (it.output != zO) {
                    println("output not wired correctly $zO ${it.output} $it")
                } else {
                    println("PASS $zO")
                }
            } ?: run { println("No Nested XOR Gate: $i $out") }

            val nestedAnd = gates.find { it.gateByInput(out, op = "AND") }?.let { nestAnd ->
                val nestedOr = gates.find { it.gateByInput(nestAnd.output, andGate?.output, "OR") }?.let { nestOr ->
                    // This should be input to next XOR it
                    val nextIStr = (i + 1).indexStr('z')
                    gates.find { it.gateByInput(nestOr.output, op = "XOR") && it.output == nextIStr } ?: run { println("OR invalid output $i ${nestOr.output}") }
                } ?: run { println("No nested OR Gate $i $nestAnd $andGate") }
            } ?: run { println("No Nested AND Gate $i $out") }
            outXor
        } ?: run { println("No XOR Gate: $i") }
    }

    return listOf("z06", "jmq", "z13", "gmh", "rqf", "cbd", "z38", "qrh").sorted().joinToString(",")
}

private fun Int.indexStr(prefix: Char): String {
    val suffix = if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
    return "$prefix$suffix"
}

private fun getStateByPrefix(state: Map<String, Int>, prefix: String): String {
    return state.keys.filter { it.startsWith(prefix) }
        .map { it.drop(1).toInt() to state[it] }
        .sortedByDescending { it.first }
        .joinToString("") { (it.second ?: 0).toString() }
}

fun main() {
    println(partOne())
    println(partTwo())
}
