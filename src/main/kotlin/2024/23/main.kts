import kotlin.Int
import util.InputParser

val inputParser = InputParser("2024/23/input.txt")
val graph = inputParser.lines().toGraph()
println(partOne())
println(partTwo())

fun partOne(): Int {
    val results = mutableSetOf<String>()
    graph.forEach { entry ->
        val comps = entry.value.toList()
        val indices = arrayOf(0, 1)
        while (indices[0] < comps.size - 1) {
            val connected = indices.map { comps[it] }
            if (connected.any { it.startsWith("t") }) {
                if (connected[1] in graph[connected.first()]!!) {
                    results.add(
                        listOf(connected[0], connected[1], entry.key)
                            .sortJoin("")
                    )
                }
            }
            if (indices[1] < comps.size - 1) {
                indices[1]++
            } else {
                indices[0]++
                indices[1] = indices[0] + 1
            }
        }
    }
    return results.size
}

private fun Collection<String>.sortJoin(delimiter: String = ","): String {
    return this.sorted().joinToString(delimiter)
}

private fun List<String>.toGraph(): Map<String, Set<String>> {
    val sets = mutableMapOf<String, MutableSet<String>>()
    this.map {
        val split = it.split("-")
        split.first() to split.last()
    }.forEach { (a, b) ->
        sets.putIfAbsent(a, mutableSetOf())
        sets[a]?.add(b)
        sets.putIfAbsent(b, mutableSetOf())
        sets[b]?.add(a)
    }
    return sets
}

fun partTwo(): String {
    return findMaximalCliques(graph)
        .maxByOrNull { it.size }
        ?.sortJoin(",")
        .orEmpty()
}

// Shamelessly stolen implementation of Bron-Kerbosch algorithm.
fun findMaximalCliques(graph: Map<String, Set<String>>): List<Set<String>> {
    val cliques = mutableListOf<Set<String>>()
    val r = mutableSetOf<String>()
    val p = graph.keys.toMutableSet()
    val x = mutableSetOf<String>()

    fun bronKerbosch(r: MutableSet<String>, p: MutableSet<String>, x: MutableSet<String>) {
        if (p.isEmpty() && x.isEmpty()) {
            cliques.add(r.toSet())
            return
        }

        for (v in p.toSet()) {
            val newR = r.toMutableSet()
            newR.add(v)
            val newP = p.intersect(graph[v] ?: emptySet()).toMutableSet()
            val newX = x.intersect(graph[v] ?: emptySet()).toMutableSet()
            bronKerbosch(newR, newP, newX)
            p.remove(v)
            x.add(v)
        }
    }

    bronKerbosch(r, p, x)
    return cliques
}
