import kotlin.Int
import util.InputParser

val inputParser = InputParser("2017/09/input.txt")
println(partOne())
println(partTwo())

fun partOne() = takeOutTheTrash().first
fun partTwo() = takeOutTheTrash().second

fun takeOutTheTrash(): Pair<Int, Int> {
    val input = inputParser.line()
    var groups = 0
    var depth = 0
    var index = 0
    var trashCount = 0
    var inTrash = false
    while(index < input.length) {
        val next = input[index]
        when {
            inTrash && next == '!' -> index++
            inTrash && next == '>' -> inTrash = false
            inTrash -> trashCount++
            next == '<' -> inTrash = true
            next == '{' -> depth++
            next == '}' -> {
                groups += depth
                depth--
            }
        }
        index++
    }
    return groups to trashCount
}
