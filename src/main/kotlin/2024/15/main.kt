package `2024`.`15`

import kotlin.Int
import util.InputParser
import util.grid.Direction
import util.grid.toGrid
import util.list.toCharGrid

val inputParser = InputParser("2024/15/input.txt")
val dirMap = Direction.instructionMap()



fun partOne(): Int {
    val grid = inputParser.chunk()[0].toCharGrid()
    val instructions = inputParser.chunk()[1].joinToString("")

    var curr = grid.indexOf('@')
    instructions.map { dirMap[it]!! }
        .forEachIndexed { index, direction ->
            grid[curr] = '.'
            var nextIndex = grid.indexByDirection(curr, direction)!!
            val setNext = nextIndex
            when (grid[nextIndex]) {
                '.' -> curr = setNext
                '#' -> { } // no-op
                else -> {
                    val intermediates = mutableSetOf<Int>()
                    while (grid[nextIndex] == 'O') {
                        intermediates.add(nextIndex)
                        nextIndex = grid.indexByDirection(nextIndex, direction)!!
                    }
                    when(grid[nextIndex]) {
                        '.' -> {
                            curr = setNext
                            intermediates.forEach {
                                grid[grid.indexByDirection(it, direction)!!] = 'O'
                            }
                        }
                    }
                }
            }
            grid[curr] = '@'
        }

    return grid.mapIndexed { index, c ->
        when (c) {
            'O' -> index
            else -> null
        }
    }.filterNotNull()
        .sumOf {
            val vec2 = grid.getVector(it)
            vec2.y * 100 + vec2.x
        }
}

fun partTwo(): Int {
    // THIS IS UGLY, NOT COMING BACK
    val grid = inputParser.chunk()[0].map {
        val charArray = it.toCharArray()
        charArray.map {
            when (it) {
                'O' -> "[]"
                '.' -> ".."
                '#' -> "##"
                '@' -> "@."
                else -> ""
            }.toCharArray().toList()
        }.flatten()
    }.toGrid()
    val instructions = inputParser.chunk()[1].joinToString("")
    var curr = grid.indexOf('@')
    instructions.map { dirMap[it]!! }
        .forEachIndexed { index, direction ->
            grid[curr] = '.'
            var nextIndex = grid.indexByDirection(curr, direction)!!
            val setNext = nextIndex
            when (grid[nextIndex]) {
                '.' -> curr = setNext
                '#' -> { } // no-op
                else -> {
                    if (direction == Direction.Left || direction == Direction.Right) {
                        val intermediates = mutableMapOf<Int, Char>()
                        while (grid[nextIndex] == '[' || grid[nextIndex] == ']') {
                            intermediates[nextIndex] = grid[nextIndex]
                            nextIndex = grid.indexByDirection(nextIndex, direction)!!
                        }
                        when(grid[nextIndex]) {
                            '.' -> {
                                curr = setNext
                                intermediates.forEach {
                                    grid[grid.indexByDirection(it.key, direction)!!] = it.value
                                }
                            }
                        }
                    } else {
                        val allNextIndices = mutableListOf<Int>()
                        val nextRowIndices = mutableListOf(nextIndex)
                        if (grid[nextIndex] == ']') nextRowIndices.addFirst(nextIndex - 1)
                        if (grid[nextIndex] == '[') nextRowIndices.addLast(nextIndex + 1)
                        allNextIndices.addAll(nextRowIndices)
                        while (nextRowIndices.isNotEmpty()) {
                            val nexts = nextRowIndices.map { grid.indexByDirection(it, direction)!! }
                                .filter { grid[it] == '[' || grid[it] == ']' }
                                .sorted().toMutableSet()

                            nexts.addAll(nexts.map {
                                when (grid[it]) {
                                    ']' -> it - 1
                                    '[' -> it + 1
                                    else -> 0
                                }
                            })
                            nextRowIndices.clear()
                            nextRowIndices.addAll(nexts)
                            allNextIndices.addAll(nextRowIndices)
                        }

                        val shouldntMove = allNextIndices.any {
                            grid[grid.indexByDirection(it, direction)!!] == '#'
                        }

                        if (!shouldntMove) {
                            curr = setNext
                            val backIndices = allNextIndices
                                .map { grid.indexByDirection(it, direction.opposite())!! to it }
                                .filter { it.first !in allNextIndices }

                            allNextIndices.map { it to grid[it] }
                                .map { grid.indexByDirection(it.first, direction)!! to it.second }
                                .forEach { grid[it.first] = it.second }
                            backIndices.forEach { grid[it.second] = '.' }
                        }
                    }
                }
            }
            grid[curr] = '@'
            println(grid)
            val validG = grid.withIndex().filter {
                if (it.value == '[') {
                    grid[it.index + 1]  != ']'
                } else false
            }
            if (validG.isNotEmpty()) {
                println(validG.map { grid.getVector(it.index) })
                return -1
            }
        }

    return grid.mapIndexed { index, c ->
        when (c) {
            '[' -> index
            else -> null
        }
    }.filterNotNull()
        .sumOf {
            val vec2 = grid.getVector(it)
            vec2.y * 100 + vec2.x
        }
}

fun main() {
    println(partOne())
    println(partTwo())
}
