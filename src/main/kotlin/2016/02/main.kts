import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import kotlin.Int

val inputParser = InputParser("2016/02/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val directionMap = Direction.directionMap()
    val keypad: Grid<Int> = listOf(1,2,3,4,5,6,7,8,9).toGrid(3)
    return inputParser.lines()
        .fold(4 to "") { acc, chars ->
            var index = acc.first
            chars.forEach { d ->
                directionMap[d]?.let { dir ->
                    keypad.indexByDirection(index, dir)?.let { index = it }
                }
            }
            index to (acc.second + keypad[index])
        }.second.toInt()
}

fun partTwo(): String {
    val directionMap = Direction.directionMap()
    val keypad: Grid<Int> = listOf(-1,-1,1,-1,-1,-1,2,3,4,-1,5,6,7,8,9,-1,10,11,12,-1,-1,-1,13,-1,-1).toGrid(5)
    return inputParser.lines()
        .fold(10 to "") { acc, chars ->
            var index = acc.first
            chars.forEach { d ->
                directionMap[d]?.let { dir ->
                    val nextIndex = keypad.indexByDirection(index, dir)
                    if (nextIndex != null && keypad[nextIndex] != -1) {
                        index = nextIndex
                    }
                }
            }
            index to (acc.second + keypad[index].toString(16))
        }.second
}
