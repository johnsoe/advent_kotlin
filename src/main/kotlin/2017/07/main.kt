package `2017`.`07`

import util.InputParser
import kotlin.Int
import kotlin.math.abs

val inputParser = InputParser("2017/07/input.txt")
val inputRegex = """^(.*) \((\d+)\)""".toRegex()

fun partOne(): String {
    val nodes = nodes()
    return rootNode(nodes)
}

private fun nodes(): List<Node> {
    return inputParser.lines()
        .map {
            val input = inputRegex.find(it)?.destructured?.toList()!!
            val outputs = if (it.contains(" -> ")) {
                it.split(" -> ").lastOrNull()?.split(", ")
            } else {
                emptyList()
            }
            Node(
                input.first(),
                input.last().toInt(),
                outputs!!,
            )
        }
}

private fun rootNode(nodes: List<Node>): String {
    return nodes.map { it.name }
        .toMutableSet()
        .apply {
            this.removeAll(
                nodes.map { it.children }.flatten().toSet(),
            )
        }
        .first()
}

private data class Node(
    val name: String,
    val weight: Int,
    val children: List<String>,
)

fun partTwo(): Int {
    val nodes = nodes()
    val rootNode = rootNode(nodes)
    val graph = nodes.associateBy { it.name }
    val weights = mutableMapOf<String, Int>()
    imbalance(graph[rootNode]!!, graph, weights)

    // This is probably way overcomplicated
    val offChildren = graph.keys.map {
        val childNodes = graph[it]?.children?.map { graph[it]!! } ?: emptyList()
        val cW = childNodes.associateWith { weights[it.name]!! }
        val avg = cW.values.average()
        cW.filterValues { it.toDouble() != avg }
    }.filter {
        it.isNotEmpty()
    }.minByOrNull { it.values.sum() }!!
    val offNode = offChildren.keys.map { outer ->
        var offCount = 0
        offChildren.keys.forEach { inner ->
            if (outer.name != inner.name && offChildren[outer] != offChildren[inner]) {
                offCount += offChildren[outer]!! - offChildren[inner]!!
            }
        }
        offCount to outer.name
    }.maxBy { abs(it.first) }
    return graph[offNode.second]!!.weight - (offNode.first / (offChildren.size - 1))
}

private fun imbalance(curr: Node, graph: Map<String, Node>, weights: MutableMap<String, Int>): Int {
    return if (curr.children.isEmpty()) {
        curr.weight
    } else {
        var sum = 0
        curr.children.forEach {
            weights[it] = imbalance(graph[it]!!, graph, weights)
            sum += weights[it]!!
        }
        sum + curr.weight
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
