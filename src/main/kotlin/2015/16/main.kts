import kotlin.Int
import util.InputParser

val inputParser = InputParser("2015/16/input.txt")
val input = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1
)
println(partOne())
println(partTwo())

fun partOne(): Int {
    return findIdByPredicate { key, value ->
        input[key] == value
    }
}

fun generateSues(): List<Sue> {
    return inputParser.lines().map { input ->
        val split = input.split(", ", ": ", " ")
        val attributeMap = split.drop(2)
            .chunked(2)
            .associate { it[0] to it[1].toInt() }

        Sue(split[1].toInt(), attributeMap)
    }
}

fun findIdByPredicate(predicate: (key: String, value: Int) -> Boolean): Int {
    return generateSues().firstOrNull { sue ->
        sue.attributes.all {
            predicate(it.key, it.value)
        }
    }?.id ?: -1
}

fun partTwo(): Int {
    return findIdByPredicate { key, value ->
        when (key) {
            "cats", "trees" -> value > (input[key] ?: 0)
            "pomeranians", "goldfish" -> value < (input[key] ?: 0)
            else -> input[key] == value
        }
    }
}

data class Sue (
    val id: Int,
    val attributes: Map<String, Int>
)
