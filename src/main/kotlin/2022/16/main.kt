package `2022`.`16`

import util.InputParser
import kotlin.Int
import kotlin.math.max

private val inputParser = InputParser("2022/16/input.txt")

// Start by generating the full map of the tunnels and then
// figuring out the weighted paths from each node, to all other nodes.
val valveMap = getValves().associateBy { it.name }
val tunnelRoutes = valveMap.map { findShortestRoutes(it.key) }
    .associateBy { route ->
        route.keys.find { route[it] == 0 }!!
    }

private fun partOne(): Long {
    val allRoutes = mutableListOf<Map<String, Int>>()
    findMostEfficientRoute("AA", 30, mutableMapOf(), allRoutes)
    return allRoutes
        .map { it.getTotalFlow() }
        .max()
}

fun getValves(): List<Valve> {
    return inputParser.lines()
        .map {
            val split = it.split(" ", "=", "; ", ", ")
            Valve(
                name = split[1],
                rate = split[5].toInt(),
                valves = split.drop(10),
            )
        }
}

fun findShortestRoutes(start: String): Map<String, Int> {
    val routes = valveMap.keys
        .associateWith { Integer.MAX_VALUE }
        .toMutableMap()
    routeHelper(start, 0, routes)
    return routes
}

fun routeHelper(current: String, count: Int, routes: MutableMap<String, Int>) {
    routes[current] = count
    val neighbors = valveMap[current]!!.valves
    neighbors.forEach {
        if (routes[it]!! > (count + 1)) {
            routeHelper(it, count + 1, routes)
        }
    }
}

fun findMostEfficientRoute(
    current: String,
    timeLeft: Int,
    opened: MutableMap<String, Int>,
    fullPaths: MutableList<Map<String, Int>>,
) {
    val options = tunnelRoutes[current]!!.keys.filter {
        !opened.containsKey(it) &&
            valveMap[it]!!.rate > 0 &&
            timeLeft - tunnelRoutes[current]!![it]!! - 1 > 0
    }
    if (options.isEmpty()) {
        fullPaths.add(mutableMapOf<String, Int>().apply { this.putAll(opened) })
    }
    options.forEach {
        val updatedTime = timeLeft - tunnelRoutes[current]!![it]!! - 1
        opened[it] = updatedTime
        findMostEfficientRoute(it, updatedTime, opened, fullPaths)
        opened.remove(it)
    }
}

fun Map<String, Int>.getTotalFlow(): Long {
    return this.filter { it.value > 0 }
        .map { valveMap[it.key]!!.rate * it.value.toLong() }
        .sum()
}

data class Valve(
    val name: String,
    val rate: Int,
    val valves: List<String>,
)

private fun partTwo(): Long {
    val allRoutes = mutableListOf<Map<String, Int>>()
    findMostEfficientRoute("AA", 26, mutableMapOf(), allRoutes)
    return allRoutes.mapIndexed { i, route ->
        if (i + 1 >= allRoutes.size) {
            0
        } else {
            allRoutes.drop(i + 1)
                .map { eRoute ->
                    val merged = mutableMapOf<String, Int> ()
                    merged.putAll(route)
                    eRoute.forEach {
                        merged[it.key] = max(it.value, merged[it.key] ?: it.value)
                    }
                    merged.getTotalFlow()
                }.max()
        }
    }.max()
}

private fun main() {
    println(partOne())
    println(partTwo())
}
