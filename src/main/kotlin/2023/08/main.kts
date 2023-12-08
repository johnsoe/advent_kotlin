import util.InputParser
import util.math.lcm

val inputParser = InputParser("2023/08/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    return "AAA".findCycleSize(
        cmds = inputParser.lines().first(),
        nodes = getNodes()
    )
}

fun getNodes(): Map<String, List<String>> {
    return inputParser.lines().drop(2)
        .associate {
            val split = it.split(" = (", ", ")
            Pair(split[0], listOf(split[1], split[2].dropLast(1)))
        }
}

fun String.findCycleSize(cmds: String, nodes: Map<String, List<String>>): Long {
    var count = 0L
    var nodeStr = this

    while(true) {
        val node = nodes[nodeStr]!!
        when (cmds[(count % cmds.length).toInt()]) {
            'L' -> nodeStr = node.first()
            'R' -> nodeStr = node.last()
        }
        count++
        if (nodeStr.endsWith("Z"))
            return count
    }
}

fun partTwo(): Long {
    val cmds = inputParser.lines().first()
    val nodes = getNodes()
    return nodes.filter { it.key.endsWith("A") }
        .map { it.key.findCycleSize(cmds, nodes) }
        .lcm()
}
