package `2018`.`07`

import kotlin.Int
import util.InputParser
import java.lang.StringBuilder

private val inputParser: InputParser = InputParser("2018/07/input.txt")

/**
 * --- Day 7: The Sum of Its Parts ---
 *
 * Completed with the help of Cursor (2 Attempts, Easily corrected for part 2 after being off by one)
 */
private data class StepGraph(val prereqs: Map<Char, Set<Char>>, val allSteps: Set<Char>)

private fun parseStepGraph(): StepGraph {
    val lines = inputParser.lines()
    val regex = Regex("Step (.) must be finished before step (.) can begin.")
    val prereqs = mutableMapOf<Char, MutableSet<Char>>()
    val allSteps = mutableSetOf<Char>()
    for (line in lines) {
        val match = regex.matchEntire(line)
        if (match != null) {
            val (pre, post) = match.destructured
            prereqs.computeIfAbsent(post[0]) { mutableSetOf() }.add(pre[0])
            allSteps.add(pre[0])
            allSteps.add(post[0])
        }
    }
    return StepGraph(prereqs, allSteps)
}

private fun partOne(): String {
    val (prereqs, allSteps) = parseStepGraph()
    val result = StringBuilder()
    val completed = mutableSetOf<Char>()
    val remaining = allSteps.toMutableSet()
    while (remaining.isNotEmpty()) {
        val available = remaining.filter { step ->
            prereqs[step]?.all { it in completed } != false
        }.sorted()
        val next = available.first()
        result.append(next)
        completed.add(next)
        remaining.remove(next)
    }
    return result.toString()
}

private fun partTwo(): Int {
    val (prereqs, allSteps) = parseStepGraph()
    data class Worker(var step: Char? = null, var doneAt: Int = 0)
    val numWorkers = 5
    val baseTime = 60
    val workers = Array(numWorkers) { Worker() }
    val completed = mutableSetOf<Char>()
    val inProgress = mutableSetOf<Char>()
    val remaining = allSteps.toMutableSet()
    var time = 0
    fun stepDuration(step: Char) = baseTime + (step - 'A' + 1)
    while (completed.size < allSteps.size) {
        for (worker in workers) {
            if (worker.step != null && worker.doneAt == time) {
                completed.add(worker.step!!)
                inProgress.remove(worker.step!!)
                worker.step = null
            }
        }
        val available = remaining.filter { step ->
            step !in inProgress && (prereqs[step]?.all { it in completed } != false)
        }.sorted()
        for (worker in workers) {
            if (worker.step == null) {
                val next = available.firstOrNull { it !in inProgress }
                if (next != null) {
                    worker.step = next
                    worker.doneAt = time + stepDuration(next)
                    inProgress.add(next)
                    remaining.remove(next)
                }
            }
        }
        val nextDone = workers.filter { it.step != null }.minOfOrNull { it.doneAt }
        if (nextDone != null && nextDone > time) {
            time = nextDone
        } else if (workers.any { it.step != null }) {
            time++
        }
    }
    return time
}

private fun main() {
    println(partOne())
    println(partTwo())
}
