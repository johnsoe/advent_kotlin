package tasks

import InputParser
import main.Grid
import main.getAllNeighborIndices
import java.util.*
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Eleven : InputParser<Int>() {
    public override fun getFileName(): String = "eleven.txt"

    public override fun first(): Int {
        val width = getInputByLine().size
        var octos = getInputByChunk("").first()
            .map { Character.getNumericValue(it) }
            .toMutableList()
        var flashSum = 0
        repeat(100) {
            octos = octos.map { it + 1 }.toMutableList()
            val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
            flashSum += flash(octos, ArrayDeque<Int>().apply { addAll(indices) }, width)
        }
        println(octos)
        return flashSum
    }

    private fun flash(data: MutableList<Int>, indexToCheck: Queue<Int>, width: Int): Int {
        var flashCount = 0
        while (!indexToCheck.isEmpty()) {
            val currentIndex = indexToCheck.remove()
            val neighbors = data.getAllNeighborIndices(currentIndex, width)
            val currentVal = data[currentIndex]
            if (currentVal > 9) {
                data[currentIndex] = 0
                flashCount++
                neighbors.forEach {
                    val neighborVal = data[it]
                    if (neighborVal != 0) {
                        data[it] = neighborVal + 1
                    }
                }
                indexToCheck.addAll(neighbors)
            }
        }
        return flashCount
    }

    public override fun second(): Int {
        val width = getInputByLine().size
        var octos = getInputByChunk("").first()
            .map { Character.getNumericValue(it) }
            .toMutableList()
        var flashSum = 0
        var count = 0
        while(flashSum != 100) {
            octos = octos.map { it + 1 }.toMutableList()
            val indices = octos.withIndex().filter { it.value > 9 }.map { it.index }
            flashSum = flash(octos, ArrayDeque<Int>().apply { addAll(indices) }, width)
            count++
        }
        return count
    }
}

public fun main(): Unit {
    println(Eleven.first())
    println(Eleven.second())
}
