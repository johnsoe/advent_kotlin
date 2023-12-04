package twentyone

import util.LegacyInputParser
import java.lang.Integer.max

object One : LegacyInputParser<Int>() {
    override fun getFileName() = "input.txt"

    override fun partOne(): Int {
        return increaseCount(getIntInputByLine())
    }
    
    private fun increaseCount(input: List<Int>): Int {
        return input.filterIndexed { index, i ->
            input[max(index - 1, 0)] < i
        }.size
    }

    override fun partTwo(): Int {
        val input = getIntInputByLine()
        val summedInput = input.mapIndexed { index, i ->
            i + (input.getOrNull(index + 1) ?: 0) + (input.getOrNull(index + 2) ?: 0)
        }
        return increaseCount(summedInput)
    }
}