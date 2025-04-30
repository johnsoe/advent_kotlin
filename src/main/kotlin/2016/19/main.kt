package `2016`.`19`

import kotlin.Int
import util.InputParser
import java.util.LinkedList

val inputParser = InputParser("2016/19/input.txt")



fun partOne(): Int {
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

fun partTwo(): Int {
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
    var count: Int = 1
)

fun main() {
    println(partOne())
    println(partTwo())
}
