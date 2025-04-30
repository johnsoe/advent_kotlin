package `2023`.`16`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import java.util.*

val inputParser = InputParser("2023/16/input.txt")



fun partOne(): Int {
    val grid = createGrid()
    return evaluateWithStartBeam(
        start = Beam(Direction.Right, 0),
        grid = grid
    )
}

fun partTwo(): Int {
    val grid = createGrid()
    return getInitialBeams(grid)
        .maxOf { evaluateWithStartBeam(it, grid) }
}

fun Char.passThrough(direction: Direction): List<Direction> {
    return when (this) {
        '/' -> {
            val next = when (direction) {
                Direction.Right -> { Direction.Up }
                Direction.Left -> { Direction.Down }
                Direction.Up -> { Direction.Right }
                Direction.Down -> { Direction.Left }
                else -> { Direction.Down }
            }
            listOf(next)
        }
        '|' -> {
            when(direction) {
                Direction.Right, Direction.Left -> listOf(Direction.Up, Direction.Down)
                else -> listOf(direction)
            }
        }
        '\\' -> {
            val next = when (direction) {
                Direction.Right -> { Direction.Down }
                Direction.Left -> { Direction.Up }
                Direction.Up -> { Direction.Left }
                Direction.Down -> { Direction.Right }
                else -> { Direction.Down }
            }
            listOf(next)
        }
        '-' -> {
            when(direction) {
                Direction.Down, Direction.Up -> listOf(Direction.Right, Direction.Left)
                else -> listOf(direction)
            }
        }
        else -> { listOf(direction) }
    }

}

data class Beam(
    var direction: Direction,
    var index: Int
)

fun evaluateWithStartBeam(start: Beam, grid: Grid<Char>): Int {
    val visited = Grid<MutableSet<Direction>>(grid.width)
        .apply { grid.forEach { _ -> this.add(mutableSetOf())} }

    val beams: Queue<Beam> = LinkedList()
    beams.add(start)
    while (beams.isNotEmpty()) {
        val beam = beams.remove()
        if (visited[beam.index].contains(beam.direction)) {
            continue
        } else {
            visited[beam.index].add(beam.direction)
        }
        val dir = grid[beam.index].passThrough(beam.direction)
        dir.forEach { d ->
            grid.indexByDirection(beam.index, d)?.let {
                beams.add(Beam(d, it))
            }
        }
    }
    return visited.count { it.isNotEmpty() }
}

fun getInitialBeams(grid: Grid<Char>): List<Beam> {
    return List(grid.getCol(0).size) { index -> Beam(Direction.Right, index * grid.width) } +
        List(grid.getCol(grid.width - 1).size) { index -> Beam(Direction.Left,(index + 1) * grid.width - 1) } +
        List(grid.getRow(0).size) { index -> Beam(Direction.Down, index) } +
        List(grid.getRow(grid.height - 1).size) { index -> Beam(Direction.Up, index + ((grid.height - 1) * grid.width)) }
}

fun createGrid(): Grid<Char> {
    var width = 0
    return inputParser.lines()
        .map { it.toCharArray().toList() }
        .apply {
            width = this.first().size
        }
        .flatten()
        .toGrid(width)
}

fun main() {
    println(partOne())
    println(partTwo())
}
