import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import kotlin.math.min

val inputParser = InputParser("2023/17/input.txt")
val cardinals = Direction.cardinalDirections()
println(partOne())
println(partTwo())

fun partOne(): Int {
    var width = 0
    val grid = inputParser.lines()
        .apply { width = this.last().length }
        .map { it.toCharArray().map { it.code - 48 } }
        .flatten()
        .toGrid(width)

    val minGrid = Grid<Int>(width)
        .apply {
            repeat(grid.size) {
                add(Integer.MAX_VALUE / 2)
            }
        }
    (0 until grid.height).forEach { println(grid.getRow(it)) }
    (0 until minGrid.height).forEach { println(minGrid.getRow(it)) }


    helper(grid, minGrid, mutableListOf(), mutableSetOf() ,0)
    (0 until minGrid.height).forEach { println(minGrid.getRow(it)) }
    return minGrid[0] - grid[0]
}

fun helper(grid: Grid<Int>, minGrid: Grid<Int>, directions: MutableList<Direction>, visited: MutableSet<Int>, index: Int) {
    if (index == grid.size - 1) {
        println("MIN: $index")
        minGrid[index] = grid[index]
    } else {
        val nextSteps = getValidDirections(grid, index, directions, visited)
        println("NEXT: $index $nextSteps")
        var tempMin = Integer.MAX_VALUE / 2
        nextSteps.forEach { (d, i) ->
            println("${minGrid[i]} $i $d")
            if (minGrid[i] == Integer.MAX_VALUE / 2) {
                directions.add(d)
                visited.add(i)
                helper(grid, minGrid, directions, visited, i)
                visited.remove(i)
                directions.removeLast()
            }
            tempMin = min(tempMin, minGrid[i])
        }
        minGrid[index] = tempMin + grid[index]
    }
}

fun getValidDirections(grid: Grid<Int>, index: Int, directions: List<Direction>, visited: Set<Int>): Map<Direction, Int> {
    val excludes = mutableListOf<Direction>()
    directions.takeLast(1).firstOrNull()?.opposite()?.apply { excludes.add(this) }
    if (directions.takeLast(3).toSet().size == 1 && directions.size >= 3) {
        excludes.add(directions.last())
    }
    return cardinals.filter { it !in excludes }
        .map {
            it to (grid.indexByDirection(index, it) ?: -1)
        }.filter { (_, i) ->
            i != -1 && i !in visited
        }.toMap()
}

fun partTwo(): Int {
    println("partTwo")
    return 0
}
