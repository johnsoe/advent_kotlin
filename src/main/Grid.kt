package main

open class Grid<T> constructor(
    open val width: Int,
    data: List<T>
) {

    open val internalData = data.toMutableList()

    fun getFullGrid(): List<T> {
        return internalData.toList()
    }

    fun getValueAtIndex(index: Int): T {
        return internalData[index]
    }

    fun getNthRow(n: Int): List<T> {
        return internalData.filterIndexed { index, _ ->
            index < (n + 1) * width && index >= n * width
        }
    }

    fun getNthCol(n: Int): List<T> {
        return internalData.filterIndexed { index, _ ->
            index % width == n
        }
    }

    fun updateFirstOfValue(toUpdate: T, update: T): Boolean {
        val index = internalData.indexOf(toUpdate)
        if (index != -1) {
            internalData[index] = update
            return true
        }
        return false
    }

    fun getNeighborIndices(index: Int): Set<Int> {
        val neighbors = mutableSetOf<Int>()
        if (index >= width) neighbors.add(index - width)
        if (index + width < internalData.size) neighbors.add(index + width)
        if (index % width != 0) neighbors.add(index - 1)
        if ((index + 1) % width != 0) neighbors.add(index + 1)
        return neighbors
    }

    fun getNeighborValues(index: Int): List<T> {
        return getNeighborIndices(index)
            .toList()
            .map { internalData[it] }
    }
}