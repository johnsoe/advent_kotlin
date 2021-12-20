package twentyone

import InputParser
import util.Grid
import util.toGrid
import java.util.*
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Eleven : InputParser<Int>() {
    public override fun getFileName(): String = "eleven.txt"

    public override fun partOne(): Int {
        val width = getInputByLine().size
        var octos = getInputByChunk("").first()
            .map { Character.getNumericValue(it) }
            .toGrid(width)
        var flashSum = 0
        repeat(100) {
            octos = octos.map { it + 1 }.toGrid(width)
            val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
            flashSum += flash(octos, ArrayDeque<Int>().apply { addAll(indices) })
        }
        println(octos)
        return flashSum
    }

    private fun flash(data: Grid<Int>, indexToCheck: Queue<Int>): Int {
        var flashCount = 0
        while (!indexToCheck.isEmpty()) {
            val currentIndex = indexToCheck.remove()
            val neighbors = data.getAllNeighbors(currentIndex)
            val currentVal = data[currentIndex]
            if (currentVal > 9) {
                data[currentIndex] = 0
                flashCount++
                neighbors.forEach {
                    if (it.value != 0) {
                        data[it.index] = it.value + 1
                    }
                }
                indexToCheck.addAll(neighbors.map { it.index })
            }
        }
        return flashCount
    }

    public override fun partTwo(): Int {
        val width = getInputByLine().size
        var octos = getInputByChunk("").first()
            .map { Character.getNumericValue(it) }
            .toGrid(width)
        var flashSum = 0
        var count = 0
        while(flashSum != 100) {
            octos = octos.map { it + 1 }.toGrid(width)
            val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
            flashSum = flash(octos, ArrayDeque<Int>().apply { addAll(indices) })
            count++
        }
        return count
    }
}

public fun main(): Unit {
    println(Eleven.partOne())
    println(Eleven.partTwo())
}
