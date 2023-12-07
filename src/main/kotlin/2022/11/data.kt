package `2022`.`11`

import java.util.LinkedList

class Monkey (
    val id: Int,
    private val items: LinkedList<Long>,
    private val operation: List<String>,
    val divTest: Int,
    private val trueMonkey: Int,
    private val falseMonkey: Int
) {

    var inspectionCount = 0L
        private set

    private fun checkTest(worry: Long): Int {
        return if (worry % divTest.toLong() == 0L) {
            trueMonkey
        } else {
            falseMonkey
        }
    }

    // Used for partOne but unscalable once the /3 is removed.
    fun inspect(): Pair<Int, Long>? {
        if (items.isEmpty()) {
            return null
        }
        inspectionCount++
        var item = items.remove()
        item = operate(item) / 3
        return checkTest(item) to item
    }

    fun smartInspect(mod: Int): Pair<Int, Long>? {
        if (items.isEmpty()) {
            return null
        }
        inspectionCount++
        var item = items.remove()
        item = operate(item) % mod
        return checkTest(item) to item
    }

    private fun operate(worry: Long): Long {
        val modifier = if (operation.last() == "old") {
            worry
        } else {
            operation.last().toLong()
        }.toLong()


        return when (operation.first()) {
            "+" -> worry + modifier
            "*" -> worry * modifier
            else -> throw UnsupportedOperationException("We only getting bigger")
        }
    }

    fun addItem(worry: Long) {
        items.add(worry)
    }
}