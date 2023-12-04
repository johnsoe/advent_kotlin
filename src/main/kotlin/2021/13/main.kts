import util.InputParser
import kotlin.Int

val inputParser = InputParser("2021/13/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val chunks = inputParser.chunk()
    val firstFold = getFolds(chunks.last()).first()
    return fold(
        firstFold.first,
        firstFold.second.toInt(),
        getPoints(chunks.first())
    ).size
}

fun getPoints(input: String): Set<Pair<Int, Int>> {
    return input.split(" ").map {
        val point = it.split(",")
        point.first().toInt() to point.last().toInt()
    }.toSet()
}

fun getFolds(input: String): List<Pair<String, String>> {
    return input.split(" ")
        .filterIndexed { index, _ -> index % 3 == 2 }
        .map {
            val fold = it.split("=")
            fold.first() to fold.last()
        }
}

fun fold(foldDirection: String, foldInt: Int, points: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    return points.map {
        when {
            foldDirection == "x" && it.first > foldInt -> it.first - ((it.first - foldInt) * 2) to it.second
            foldDirection == "y" && it.second > foldInt -> it.first to it.second - ((it.second - foldInt) * 2)
            else -> it
        }
    }.toSet()

}

fun partTwo(): Int {
    val chunks = inputParser.chunk()
    val folded = getFolds(chunks.last())
        .fold(getPoints(chunks.first())) { acc, next ->
            fold(next.first, next.second.toInt(), acc)
        }
    for (i in 0..folded.maxOf { it.second }) {
        for (j in 0 .. folded.maxOf { it.first }) {
            if (folded.contains(j to i)) {
                print("#")
            } else {
                print(" ")
            }
        }
        println()
    }
    return 0
}
