import kotlin.Int
import util.InputParser
import util.math.isEven
import java.util.LinkedList

val inputParser = InputParser("2024/11/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val queue = LinkedList<Long>()
    queue.addAll(inputParser.linesOfType<Long>().flatten())
    repeat(25) {
        val temp = LinkedList<Long>()
        while(queue.isNotEmpty()) {
            val next = queue.removeFirst()
            temp.addAll(nextStones(next))
        }
        queue.addAll(temp)
    }
    return queue.size
}

fun nextStones(stone: Long): List<Long> {
    return when {
        stone == 0L -> listOf(1)
        stone.digitCount().isEven() -> listOf(stone.halve(true), stone.halve(false))
        else -> listOf( stone * 2024)
    }
}

private fun Long.halve(front: Boolean): Long {
    val str = this.toString()
    val length = str.length
    val (a, b) =  if (front) {
        0 to length / 2
    } else {
        length / 2 to length
    }
    return str.substring(a, b).toLong()
}
private fun Long.digitCount(): Int = this.toString().length

fun partTwo(): Long {
    val queue = mutableMapOf<Long, Long>()
    val input = inputParser.linesOfType<Long>().flatten()
    input.forEach { queue[it] = 1 }
    repeat(75) { _ ->
        val temp = mutableMapOf<Long, Long>()
        queue.keys.forEach { key ->
            val counts = queue[key]!!
            nextStones(key).forEach {
                temp.putIfAbsent(it, 0)
                temp[it] = temp[it]!! + counts
            }
        }
        queue.clear()
        queue.putAll(temp)
    }
    return queue.values.sum()
}
