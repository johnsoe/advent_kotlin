package tasks

import InputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Twelve : InputParser<Int>() {
    public override fun getFileName(): String = "twelve.txt"

    public override fun first(): Int {
        return generatePaths(
            generateCaves(),
            mutableListOf("start"),
            ::canVisitCavePartOne
        ).size
    }

    private fun generateCaves(): Map<String, Set<String>> {
        val caves = mutableMapOf<String, MutableSet<String>>()
        getInputByLine().map { it.split("-") }
            .forEach {
                val front = it.first()
                val back = it.last()
                caves.putIfAbsent(front, mutableSetOf())
                caves[front]!!.add(back)

                caves.putIfAbsent(back, mutableSetOf())
                caves[back]!!.add(front)
            }
        return caves
    }

    private fun generatePaths(
        caves: Map<String, Set<String>>,
        path: MutableList<String>,
        pathCheck: (String, List<String>) -> Boolean
    ): Set<String> {
        if (path.contains("end")) {
            return setOf(path.joinToString())
        } else {
            val cave = caves[path.last()]
            val subsets = mutableSetOf<String>()
            cave?.forEach {
                if (pathCheck(it, path)) {
                    path.add(it)
                    subsets.addAll(generatePaths(caves, path, pathCheck))
                    path.removeLast()
                }
            }
            return subsets
        }
    }

    public override fun second(): Int {
        return generatePaths(
            generateCaves(),
            mutableListOf("start"),
            ::canVisitCavePartTwo
        ).size
    }
}

fun String.isBigCave(): Boolean {
    return this.toCharArray().all { it.isUpperCase() }
}

fun canVisitCavePartOne(cave: String, path: List<String>): Boolean {
    return cave.isBigCave() || !path.contains(cave)
}

fun canVisitCavePartTwo(cave: String, path: List<String>): Boolean {
    if (cave.isBigCave() || cave == "end") return true
    if (cave == "start") return false

    val smallCaves = path.filter { !it.isBigCave() }
    val smallCaveSet = smallCaves.toSet()
    return smallCaveSet.size == smallCaves.size || !path.contains(cave)
}

public fun main(): Unit {
    println(Twelve.first())
    println(Twelve.second())
}
