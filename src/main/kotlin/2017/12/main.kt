package `2017`.`12`

import util.InputParser
import java.util.*
import kotlin.Int

private val inputParser = InputParser("2017/12/input.txt")

private fun partOne() = graph().parseFrom("0").size

private fun graph(): Map<String, List<String>> {
    val graph = mutableMapOf<String, List<String>>()
    inputParser.lines()
        .forEach {
            val node = it.split(" <-> ")
            graph[node[0]] = node[1].split(", ")
        }
    return graph
}

private fun Map<String, List<String>>.parseFrom(start: String): Set<String> {
    val groupSet = mutableSetOf<String>()
    val toVisit = LinkedList<String>().apply { add(start) }
    while (toVisit.isNotEmpty()) {
        this[toVisit.removeFirst()]?.forEach {
            if (it !in groupSet) {
                toVisit.add(it)
                groupSet.add(it)
            }
        }
    }
    return groupSet
}

private fun partTwo(): Int {
    val graph = graph()
    val allNodes = mutableSetOf<String>()
    var count = 0
    graph.keys.forEach {
        if (it !in allNodes) {
            count++
            allNodes.addAll(graph.parseFrom(it))
        }
    }
    return count
}

private fun main() {
    println(partOne())
    println(partTwo())
}
