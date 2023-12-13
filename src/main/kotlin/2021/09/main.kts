import util.InputParser
import kotlin.Int

val inputParser = InputParser("2021/09/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
//        val grid = Grid(getInputByLine().first().length, getGridsSingleLine())
//        return grid.getFullGrid().filterIndexed { index, i ->
//            grid.getNeighborIndices(index).all { i < grid.getValueAtIndex(it) }
//        }.sumOf { it + 1 }
    return 0
}

fun getGridsSingleLine(): List<Int> {
    return inputParser.chunkAndJoin().joinToString("")
        .toCharArray()
        .map { it.code - 48 }
        .filter { it != -16 }
}

fun getNeighborIndices(index: Int, width: Int, size: Int): Set<Int> {
    val neighbors = mutableSetOf<Int>()
    if (index >= width) neighbors.add(index - width)
    if (index + width < size) neighbors.add(index + width)
    if (index % width != 0) neighbors.add(index - 1)
    if ((index + 1) % width != 0) neighbors.add(index + 1)
    return neighbors
}

fun partTwo(): Int {
//        val grid = Grid(getInputByLine().first().length, getGridsSingleLine())
//        return grid.getFullGrid().mapIndexed { index, i ->
//            if (grid.getNeighborIndices(index).all { i < grid.getValueAtIndex(it) }) {
//                getBasinSize(grid, LinkedList<Int>().apply { add(index) }, mutableSetOf())
//            } else {
//                1
//            }
//        }.sortedDescending()
//            .take(3)
//            .reduce { acc, i -> acc * i }
    return 0
}

//    private fun getBasinSize(grid: Grid<Int>, toCheck: Queue<Int>, checked: MutableSet<Int>): Int {
//        return if (toCheck.isEmpty()) {
//            checked.size
//        } else {
//            val index = toCheck.remove()
//            if (grid.getValueAtIndex(index) != 9 && !checked.contains(index)) {
//                checked.add(index)
//                toCheck.addAll(grid.getNeighborIndices(index))
//            }
//            getBasinSize(grid, toCheck, checked)
//        }
//    }
