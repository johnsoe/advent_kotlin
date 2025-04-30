package `2023`.`20`

import kotlin.Int
import util.InputParser
import `2023`.`20`.*
import java.util.*

val inputParser = InputParser("2023/20/input.txt")

fun lmao(): Double {
    var i = .1
    var chance = 1.0
    while (i > 0.0) {
        i -= 0.01
        chance *= i
    }
    return chance
}

fun partOne(): Int {
//    val pulseCounts = mutableMapOf(
//        Pulse.LOW to 0,
//        Pulse.HIGH to 0,
//    )
//    val nodes = generateGraph()
//    val queue = LinkedList<Pair<String, Pulse>>()
//    repeat(1) {
//        //Button press
//        //pulseCounts[Pulse.LOW] = pulseCounts[Pulse.LOW]!! + 1
//        queue.add("broadcaster" to Pulse.LOW)
//        while (queue.isNotEmpty()) {
//            val next = queue.removeFirst()
//            val node = nodes[next.first]
//            println(next)
//            node?.let {
//                pulseCounts[next.second] = (pulseCounts[next.second] ?: 0) + 1
//                when (it.module) {
//                    Module.UNTYPED -> {
//                        it.nodes.forEach { subNode -> queue.add(subNode to it.pulse) }
//                    }
//                    Module.CONJUNCTION -> {
//                        // Check all subnodes for state
//                        val result = it.nodes.all { subNode ->
//                            nodes[subNode]?.let { sub ->
//                                sub.pulse == Pulse.HIGH
//                            } ?: true
//                        }
//                        it.pulse = if (result) Pulse.LOW else Pulse.HIGH
//                        //println(it.pulse)
//                        it.nodes.forEach { subNode -> queue.add(subNode to it.pulse) }
//                    }
//                    Module.FLIP_FLOP -> {
//                        if (next.second == Pulse.LOW) {
//                            if (it.pulse == Pulse.HIGH) {
//                                it.pulse = Pulse.LOW
//                            } else {
//                                it.pulse = Pulse.HIGH
//                            }
//                            //println(it.pulse)
//                            it.nodes.forEach { subNode -> queue.add(subNode to it.pulse) }
//                        }
//                    }
//                }
//            } ?: run { pulseCounts[next.second] = pulseCounts[next.second]!! + 1 }
//        }
//    }
//    println(pulseCounts)
//    return (pulseCounts[Pulse.HIGH] ?: 0) * (pulseCounts[Pulse.LOW] ?: 0)
    return 0
}

//fun generateGraph(): Map<String, Node> {
//    val nodeMap = mutableMapOf<String, Node>()
//    inputParser.lines()
//        .map { it.split(" -> ", ", ") }
//        .map {
//            val name = it.first()
//            val type = when (name.first()) {
//                '%' -> Module.FLIP_FLOP to 1
//                '&' -> Module.CONJUNCTION to 1
//                else -> Module.UNTYPED to 0
//            }
//            nodeMap[name.drop(type.second)] = Node(
//                name.drop(type.second),
//                it.drop(1),
//                module = type.first
//            )
//        }
//    return nodeMap
//}

fun partTwo(): Int {

    return 0
}

fun main() {
    println(partOne())
    println(partTwo())
}
