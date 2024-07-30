import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/22/input.txt")
val regex = """/dev/grid/node-x(\d+)-y(\d+)\s+\d+T\s+(\d+)T\s+(\d+)T""".toRegex()
println(partOne())
println(partTwo())

fun partOne(): Int {
    val nodes = generateNodes()
    return nodes.sumOf {
        nodes.count { inner ->
            it.isViablePair(inner)
        }
    }
}

private fun generateNodes(): List<Node> {
    return inputParser.lines().drop(2)
        .map { input ->
            val matchResult = regex.find(input)!!
            val groups = matchResult.destructured
                .toList()
                .map { it.toInt() }
            Node(groups[0], groups[1], groups[2], groups[3])
        }
}

private data class Node (
    val x: Int,
    val y: Int,
    val used: Int,
    val avail: Int
) {
    fun isViablePair(other: Node): Boolean {
        return other != this &&
                this.used != 0 &&
                this.used <= other.avail
    }

    fun isLargeNode(): Boolean {
        return this.used > 400
    }

    fun isEmpty(): Boolean {
        return this.used == 0
    }
}

// Not a completely programmatic solution. Printed the grid and counted.
fun partTwo(): Int {
    val nodes = generateNodes()
        .sortedWith(
            compareBy({ it.y }, { it.x })
        )
    var prevY = 0
    nodes.forEach {
        if (it.y != prevY) {
            println()
            prevY = it.y
        }
        val outChar = when {
            it.isLargeNode() -> '#'
            it.isEmpty() -> '_'
            else -> '.'
        }
        print(outChar)
    }
    return -1
}
