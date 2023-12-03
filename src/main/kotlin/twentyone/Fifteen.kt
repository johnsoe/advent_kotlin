package twentyone

import LegacyInputParser
import java.util.*
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.math.min

public object Fifteen : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "twentyone/input/fifteen.txt"

    public override fun partOne(): Int {
//        val grid = Grid(getInputByLine().first().length, getGridsSingleLine(getInputByChunk()))
//        val size = grid.getFullGrid().size
//        val dp = IntArray(size) { -1 }.apply { this[size - 1] = grid.getFullGrid().last() }
//        return traverseGrid(LinkedList<Int>().apply { add(size - 1) }, dp, grid)
        return 0
    }

//    private fun traverseGrid(queue: LinkedList<Int>, dp: IntArray,  grid: Grid<Int>): Int {
//        while (queue.isNotEmpty()) {
//            val index = queue.remove()
//            val toCheck = grid.getNeighborIndices(index).filter {
//                val current = dp[it]
//                val travel = dp[index] + grid.getValueAtIndex(it)
//                if (dp[it] == -1) {
//                    dp[it] = travel
//                } else {
//                    dp[it] = min(dp[it], travel)
//                }
//                current != dp[it]
//            }.filter { !queue.contains(it) }
//            queue.addAll(toCheck)
//        }
//        return dp.first() - grid.internalData.first()
//    }

    private fun getGridsSingleLine(chunks: List<String>): List<Int> {
        return chunks.joinToString("")
            .toCharArray()
            .map { it.toInt() - 48 }
            .filter { it != -16 }
    }

    public override fun partTwo(): Int {
//        val expanded = expandInput()
//        val grid = Grid(expanded.first().length, getGridsSingleLine(expanded))
//        val size = grid.getFullGrid().size
//        val dp = IntArray(size) { -1 }.apply { this[size - 1] = grid.getFullGrid().last() }
//        return traverseGrid(LinkedList<Int>().apply { add(size - 1) }, dp, grid)
        return 0
    }

    private fun expandInput(): List<String> {
        val expanded = getInputByLine().map {
            var combined = it
            repeat(4) { rep ->
                combined = "$combined${mapInput(it, rep + 1)}"
            }
            combined
        }.toMutableList()
        val toAdd = mutableListOf<String>().apply { addAll(expanded) }
        repeat(4) { rep ->
            toAdd.addAll(
                expanded.map { mapInput(it, rep + 1) }
            )
        }
        return toAdd
    }

    private fun mapInput(input: String, offset: Int): String {
        return input.toCharArray().map {
            val updated = (it.toInt() - 48 + offset)
            if (updated > 9) updated % 9 else updated
        }.joinToString("")
    }
}

public fun main(): Unit {
    println(Fifteen.partOne())
    println(Fifteen.partTwo())
}
