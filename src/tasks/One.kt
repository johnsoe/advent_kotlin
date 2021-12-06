package tasks

import InputParser
import java.lang.Integer.max

object One : InputParser() {
    override fun getFileName() = "one.txt"

    override fun first(): Int {
        return increaseCount(getIntInputByLine())
    }
    
    private fun increaseCount(input: List<Int>): Int {
        return input.filterIndexed { index, i ->
            input[max(index - 1, 0)] < i
        }.size
    }

    override fun second(): Int {
        val input = getIntInputByLine()
        val summedInput = input.mapIndexed { index, i ->
            i + (input.getOrNull(index + 1) ?: 0) + (input.getOrNull(index + 2) ?: 0)
        }
        return increaseCount(summedInput)
    }
}