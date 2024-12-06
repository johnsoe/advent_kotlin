import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid

val inputParser = InputParser("2024/06/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val grid = inputParser.charGrid()
    return traverse(
        grid,
        grid.indexOf('^'),
    ).size
}

fun traverse(
    grid: Grid<Char>,
    initialPosition: Int,
    obstructionIndex: Int? = null,
    onLoopDetected: (() -> Unit)? = null,
): Map<Int, Direction> {
    var position = initialPosition
    var direction: Direction = Direction.Up
    val visited = mutableMapOf(position to direction)
    while (true) {
        val next = grid.indexByDirection(position, direction) ?: break
        if (visited[next] == direction) {
            onLoopDetected?.invoke()
            break
        }
        if (grid[next] == '#' || next == obstructionIndex) {
            direction = direction.turnRight()
        } else {
            position = next
            visited[next] = direction
        }
    }
    return visited
}

fun partTwo(): Int {
    val grid = inputParser.charGrid()
    val initialPosition = grid.indexOf('^')
    val visited = traverse(grid, initialPosition).toMutableMap()

    visited.remove(initialPosition)
    var count = 0
    visited.keys.forEach { obstruction ->
        traverse(
            grid,
            initialPosition,
            obstruction
        ) { count++ }
    }
    return count
}
