package `2023`.`14`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.Grid
import util.grid.toGrid
import util.math.Vector2

val inputParser = InputParser("2023/14/input.txt")



fun partOne(): Int {
    val grid = createGrid()
    return (0 until grid.width).map {
        grid.getCol(it)
    }.map {
        val indices = it.mapIndexedNotNull { index, c ->
            if (c == '#') {
                index
            } else {
                null
            }
        }
        it to indices
    }.sumOf { (col, cubes) ->
        var sum = 0
        //Grab the first rock, if any, get all "0" in range.
        val section = if (cubes.isEmpty()) {
            col
        } else {
            col.subList(0, cubes.first())
        }
        sum += section.getSubsectionSum(grid.height)
        //println("front $sum")

        //For all middle rocks, get all "0" in range from current to next
        sum += cubes
            .dropLast(1)
            .mapIndexed { index, cube ->
                col.subList(cube, cubes[index + 1]).getSubsectionSum(grid.height - 1 - cube)
            }.sum()
        //println("mid $sum")

        //Last rocks
        if (cubes.isNotEmpty()) {
            sum += col.subList(cubes.last(), grid.height).getSubsectionSum(grid.height - 1 - cubes.last())
        }

        sum
    }

}

fun List<Char>.getSubsectionSum(lower: Int): Int {
    val count = this.count { it == 'O' }
    return (lower downTo lower - count + 1).sum()
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

fun partTwo(): Long {
    val grid = createGrid()
    val directions = listOf(Direction.Up, Direction.Left, Direction.Down, Direction.Right)
    val directionMaps = directions.associateWith { createDirectionMap(it, grid) }

    val repeated: MutableMap<String, Int> = mutableMapOf()
    var rocks = grid.getRockPoints()

    repeat(1000000000) {
        directions.forEach { d ->
            var updatedRockSet = mutableSetOf<Vector2>()
            val directionMap = directionMaps[d]!!
            rocks.forEach {
                var newPoint = directionMap[it]!!
                while (updatedRockSet.contains(newPoint)) {
                    newPoint = d.offset(newPoint)
                }
                updatedRockSet.add(newPoint)
            }
            rocks = updatedRockSet
        }
        val check = grid.mapIndexed { index, _ ->
            if (rocks.contains(grid.getVector(index))) {
                '1'
            } else {
                '0'
            }
        }.joinToString("")
        if (repeated.contains(check)) {
//            repeated.forEach {
//                println(it.value)
//                println(it.key)
//                println(it.key.getSum(grid.height))
//                println()
//            }
            val cycle = it - repeated[check]!! + 1
            val x = 1000000000 % (cycle)
            //println(x)
            val y = repeated.filter { it.value == x }.keys.first()
//            println("We've done it: $it")
//            println(check.sortedBy { it.x })
            println(y)
            return it.toLong()
        } else {
            repeated[check] = it
        }

        if (it % 10000 == 0) {
            println(it)
        }
    }

    return rocks.getSum(grid.height)
}

fun Set<Vector2>.getSum(height: Int): Long {
    return this.sumOf { height.toLong() - it.y }
}

fun Direction.offset(vec: Vector2): Vector2 {
    return this.toVec() + vec
}

fun Grid<Char>.getRockPoints(): Set<Vector2> {
    return this.mapIndexedNotNull { index, c ->
        if (c == 'O') {
            this.getVector(index)
        } else {
            null
        }
    }.toSet()
}

fun createDirectionMap(direction: Direction, grid: Grid<Char>): Map<Vector2, Vector2> {
    return grid.mapIndexed { index, _ ->
        var tempIndex = index
        while(true) {
            val next = grid.indexByDirection(tempIndex, direction)
            if (next == null || grid[next] == '#') {
                break
            }
            tempIndex = next
        }
        grid.getVector(index) to grid.getVector(tempIndex)
    }.toMap()
}

fun main() {
    println(partOne())
    println(partTwo())
}
