package `2016`.`19`

import util.InputParser
import java.util.LinkedList
import kotlin.Int

private val inputParser = InputParser("2016/19/input.txt")

private fun partOne(): Int {
    val count = inputParser.line().toInt()
    val queue = LinkedList<Elf>()
        .apply {
            repeat(count) { add(Elf(it + 1)) }
        }

    while (queue.size > 1) {
        val first = queue.removeFirst()
        val second = queue.removeFirst()
        first.count += second.count
        queue.add(first)
    }
    return queue.first().id
}

private fun partTwo(): Int {
    val count = inputParser.line().toInt()
    val queue = MutableList(count) {
        Elf(it + 1)
    }

    while (queue.size > 1) {
        // This is wildly inefficient. Shouldve implemented a mid pointer to manually controlled linked list.
        val second = queue.removeAt(queue.size / 2)
        val first = queue.removeFirst()
        first.count += second.count
        queue.add(first)
    }
    return queue.first().id
}

data class Elf(
    val id: Int,
    var count: Int = 1,
)

private fun main() {
    println(partOne())
    println(partTwo())
}
