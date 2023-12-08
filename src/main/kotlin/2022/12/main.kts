import kotlin.Int
import util.InputParser
import java.awt.Point

val inputParser = InputParser("2022/12/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val (heights, start, end) = getHeightMap()
    val visited = heights.map {
        it.map { Integer.MAX_VALUE }.toMutableList()
    }
    exploreGrid(start, heights, visited, 0, false)
    return visited[end.y][end.x]
}

fun exploreGrid(
    pos: Point,
    grid: List<List<Int>>,
    visited: List<MutableList<Int>>,
    stepCount: Int,
    reverse: Boolean
) {
    val prevStepsToPos = visited[pos.y][pos.x]
    if (stepCount < prevStepsToPos) {
        visited[pos.y][pos.x] = stepCount
        val gridHeight = grid[pos.y][pos.x]
        getNeighbors(pos, grid)
            .filter {
                val pHeight = grid[it.y][it.x]
                val toFilter = if (reverse) {
                    pHeight >= gridHeight - 1
                } else {
                    pHeight <= gridHeight + 1
                }
                toFilter
            }
            .forEach { exploreGrid(it, grid, visited, stepCount + 1, reverse) }
    }
}

fun getNeighbors(pos: Point, grid: List<List<Int>>): List<Point> {
    val neighbors = mutableListOf<Point>()
    if (pos.x != 0) {
        neighbors.add(Point(pos.x - 1, pos.y))
    }
    if (pos.y != 0) {
        neighbors.add(Point(pos.x, pos.y - 1))
    }
    if (pos.x != grid.first().size - 1) {
        neighbors.add(Point(pos.x + 1, pos.y))
    }
    if (pos.y != grid.size - 1) {
        neighbors.add(Point(pos.x, pos.y + 1))
    }
    return neighbors
}

fun getHeightMap(): Triple<List<List<Int>>, Point, Point> {
    var s: Point? = null
    var e: Point? = null
    val grid = inputParser.lines()
        .mapIndexed { y, line ->
            line.mapIndexed { x, point ->
                when (point) {
                    'S' -> {
                        s = Point(x, y)
                        point.code - 83
                    }
                    'E' -> {
                        e = Point(x, y)
                        point.code - 44
                    }
                    else -> point.code - 97
                }
            }
        }
    return Triple(grid, s!!, e!!)
}

fun partTwo(): Int {
    val (heights, _, end) = getHeightMap()
    val visited = heights.map {
        it.map { Integer.MAX_VALUE }.toMutableList()
    }
    exploreGrid(end, heights, visited, 0, true)
    return heights.mapIndexed { y, row ->
        row.mapIndexed { x, h ->
            if (h == 0) {
                visited[y][x]
            } else {
                Integer.MAX_VALUE
            }
        }.min()
    }.min()
}
