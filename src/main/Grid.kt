package main

open class Grid<T> constructor(
    open val width: Int,
    data: List<T>
) {

    public constructor(data: List<List<T>>) : this(
        data.first().size, data.flatten()
    )

    open var internalData = data.toMutableList()

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

    fun getAllNeighborIndices(index: Int): Set<Int> {
        val neighbors = mutableSetOf<Int>().apply { addAll(getNeighborIndices(index)) }
        if (index >= width && index % width != 0) neighbors.add(index - width - 1)
        if (index >= width && (index + 1) % width != 0) neighbors.add(index - width + 1)
        if (index + width < internalData.size && index % width != 0) neighbors.add(index + width - 1)
        if (index + width < internalData.size && (index + 1) % width != 0) neighbors.add(index + width + 1)
        return neighbors
    }

    fun getAllNeighborValues(index: Int): List<T> {
        return getAllNeighborIndices(index)
            .toList()
            .map { internalData[it] }
    }
}

fun <T> List<T>.getNeighborIndices(index: Int, width: Int): Set<Int> {
    val neighbors = mutableSetOf<Int>()
    if (index >= width) neighbors.add(index - width)
    if (index + width < this.size) neighbors.add(index + width)
    if (index % width != 0) neighbors.add(index - 1)
    if ((index + 1) % width != 0) neighbors.add(index + 1)
    return neighbors
}

fun <T> List<T>.getNeighborValues(index: Int, width: Int): List<T> {
    return getNeighborIndices(index, width)
        .toList()
        .map { this[it] }
}

fun <T> List<T>.getAllNeighborIndices(index: Int, width: Int): Set<Int> {
    val neighbors = mutableSetOf<Int>().apply { addAll(getNeighborIndices(index, width)) }
    if (index >= width && index % width != 0) neighbors.add(index - width - 1)
    if (index >= width && (index + 1) % width != 0) neighbors.add(index - width + 1)
    if (index + width < this.size && index % width != 0) neighbors.add(index + width - 1)
    if (index + width < this.size && (index + 1) % width != 0) neighbors.add(index + width + 1)
    return neighbors
}

fun <T> List<T>.getAllNeighborValues(index: Int, width: Int): List<T> {
    return getAllNeighborIndices(index, width)
        .toList()
        .map { this[it] }
}