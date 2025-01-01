
import kotlin.Int
import util.InputParser
import util.hash.knotReverse
import util.hash.toKnotHash

val inputParser = InputParser("2017/10/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    var index = 0
    val arr = IntArray(256) { it }
    inputParser.linesOfType<Int>(",")
        .flatten()
        .forEachIndexed { skip, sub ->
            arr.knotReverse(index, (index + sub) % 256)
            index = (index + sub + skip) % 256
        }
    return arr[0] * arr[1]
}

fun partTwo() = inputParser.line().toKnotHash()
