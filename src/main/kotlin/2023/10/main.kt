package `2023`.`10`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid

val inputParser = InputParser("2023/10/input.txt")
val directionMap: Map<Direction, Map<Char, Direction>> = mapOf(
    Direction.Up to mapOf('7' to Direction.Left, '|' to Direction.Up, 'F' to Direction.Right),
    Direction.Left to mapOf('-' to Direction.Left, 'F' to Direction.Down, 'L' to Direction.Up),
    Direction.Down to mapOf('|' to Direction.Down, 'J' to Direction.Left, 'L' to Direction.Right),
    Direction.Right to mapOf('7' to Direction.Down, '-' to Direction.Right, 'J' to Direction.Up),
)




fun partOne(): Int {
    val pipes = traversePipes(createGrid())
    return pipes.size / 2
}

fun createGrid(): Grid<Char> {
    return inputParser.lines()
        .map { it.toCharArray().toList() }
        .flatten()
        .toGrid(inputParser.lines().first().length)
}

fun traversePipes(grid: Grid<Char>): Set<Int> {
    val pipes = mutableSetOf<Int>()
    var index = grid.indexOf('S')
    var direction: Direction? = grid.getStartDirection(index)
    while (direction != null) {
        pipes.add(index)
        index = grid.indexByDirection(index, direction!!) ?: throw IllegalStateException("Something went wrong")
        direction = grid[index].nextDirection(direction!!)
    }
    return pipes
}

fun Grid<Char>.getStartDirection(index: Int): Direction {
    return Direction.cardinalDirections().firstNotNullOf { d ->
        this.indexByDirection(index, d)?.let {
            if (this[it].nextDirection(d) != null) {
                d
            } else null
        }
    }
}

fun Char.nextDirection(cDirection: Direction): Direction? {
    return directionMap[cDirection]?.get(this)
}

fun partTwo(): Int {
    val grid = createGrid()
    val pipes = traversePipes(grid)

    var containedCount = 0
    var crossCount = 0
    grid.forEachIndexed { i, c ->
        if ((i + 1) % grid.width != 0) {
            if (c in "F7|S" && i in pipes) {
                crossCount++
            } else if (crossCount % 2 == 1 && i !in pipes) {
                containedCount++
            }
        } else {
            crossCount = 0
        }
    }
    return containedCount
}

fun main() {
    println(partOne())
    println(partTwo())
}
