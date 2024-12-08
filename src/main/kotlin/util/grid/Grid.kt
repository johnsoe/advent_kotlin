package util.grid

import util.math.Vector2

class Grid<T>(
    val width: Int
) : ArrayList<T>() {

    val height: Int
        get() = size / width

    fun getAdjacentNeighbors(index: Int): Set<IndexedValue<T>> {
        return Direction.cardinalDirections().getNeighbors(index)
    }

    fun getAllNeighbors(index: Int): Set<IndexedValue<T>> {
        return Direction.allDirections().getNeighbors(index)
    }

    private fun List<Direction>.getNeighbors(index: Int): Set<IndexedValue<T>> {
        return this.mapNotNull { direction ->
            indexByDirection(index, direction)?.let {
                IndexedValue(it, this@Grid[it])
            }
        }.toSet()
    }

    fun indexByDirection(
        index: Int,
        direction: Direction,
        spacing: Int = 1
    ): Int? {
        return when (direction) {
            Direction.Up -> ::getUpNeighbor
            Direction.Down -> ::getDownNeighbor
            Direction.Left -> ::getLeftNeighbor
            Direction.Right -> ::getRightNeighbor
            Direction.UpRight -> ::getUpRightNeighbor
            Direction.DownRight -> ::getDownRightNeighbor
            Direction.UpLeft -> ::getUpLeftNeighbor
            Direction.DownLeft -> ::getDownLeftNeighbor
        }.invoke(index, spacing)
    }

    private fun getUpNeighbor(index: Int, spacing: Int): Int? {
        val neighbor = index - (width * spacing)
        return if (neighbor >= 0) neighbor else null
    }

    private fun getDownNeighbor(index: Int, spacing: Int): Int? {
        val neighbor = index + (width * spacing)
        return if (neighbor < size) neighbor else null
    }

    private fun getRightNeighbor(index: Int, spacing: Int): Int? {
        val neighbor = index + spacing
        return if (neighbor / width != index / width) {
            null
        } else {
            neighbor
        }
    }

    private fun getLeftNeighbor(index: Int, spacing: Int): Int? {
        val neighbor = index - spacing
        return if (neighbor < 0 || neighbor / width != index / width) {
            null
        } else {
            neighbor
        }
    }

    private fun getUpRightNeighbor(index: Int, spacing: Int): Int? {
        return getUpNeighbor(index, spacing)?.let {
            getRightNeighbor(it, spacing)
        }
    }

    private fun getUpLeftNeighbor(index: Int, spacing: Int): Int? {
        return getUpNeighbor(index, spacing)?.let {
            getLeftNeighbor(it, spacing)
        }
    }

    private fun getDownRightNeighbor(index: Int, spacing: Int): Int? {
        return getDownNeighbor(index, spacing)?.let {
            getRightNeighbor(it, spacing)
        }
    }

    private fun getDownLeftNeighbor(index: Int, spacing: Int): Int? {
        return getDownNeighbor(index, spacing)?.let {
            getLeftNeighbor(it, spacing)
        }
    }

    fun getGridAs2D(): List<List<T>> {
        val grid = mutableListOf<List<T>>()
        for (i in 0 until size/width) {
            val slice = this.slice(i*width until (i+1)*width)
            grid.add(i, mutableListOf<T>().apply { this.addAll(slice) })
        }
        return grid
    }

    override fun toString(): String {
        return this.chunked(width)
            .joinToString("\n") + "\n"
    }

    // Won't work if index > row size
    fun getCol(colIndex: Int): List<T> {
        return this.filterIndexed { index, _ ->
            index % width == colIndex
        }
    }

    fun getRow(rowIndex: Int): List<T> {
        return this.subList(rowIndex * width, (rowIndex + 1) * width)
    }

    private fun getRowIndices(rowIndex: Int): List<Int> {
        val range = (rowIndex * width)until((rowIndex + 1) * width)
        return range.toList()
    }

    private fun getColIndices(colIndex: Int): List<Int> {
        return (0 until size).filter {
            it % width == colIndex
        }
    }

    fun getVector(index: Int): Vector2 {
        return Vector2(index % width, index / width)
    }

    fun indexByCoords(x: Int, y: Int) =
        when {
            (y < 0 || y >= width) -> null
            (x < 0 || x >= height) -> null
            else -> y * width + x
        }

    fun indexByVector(vector2: Vector2) = indexByCoords(vector2.x, vector2.y)

    fun shiftRow(row: Int, shift: Int) {
        val indices = this.getRowIndices(row)
        val currentRow = mutableListOf<T>().also {
            it.addAll(getRow(row))
        }
        indices.forEachIndexed { index, _ ->
            val gridIndex = indices[(index + shift) % this.width]
            this[gridIndex] = currentRow[index]
        }
    }

    fun shiftCol(col: Int, shift: Int) {
        val indices = this.getColIndices(col)
        val currentCol = mutableListOf<T>().also {
            it.addAll(getCol(col))
        }
        indices.forEachIndexed { index, _ ->
            val gridIndex = indices[(index + shift) % this.height]
            this[gridIndex] = currentCol[index]
        }
    }

    fun setCorners(update: T) {
        this[0] = update // Top left
        this[this.width - 1] = update // top right
        this[this.size - this.width] = update // bottom left
        this[this.size - 1] = update // bottom right
    }

    fun deltaOfIndices(a: Int, b: Int): Vector2 {
        return getVector(a) - getVector(b)
    }
}



fun <T> Collection<T>.toGrid(width: Int): Grid<T> {
    val collection = this
    return Grid<T>(width).apply { addAll(collection) }
}

fun <T> Array<Array<T>>.toGrid(): Grid<T> {
    if (this.isEmpty()) return Grid(0)

    val array = this
    return Grid<T>(array[0].size).apply {
        array.forEach {
            addAll(it)
        }
    }
}

fun <T> List<List<T>>.toGrid(): Grid<T> {
    if (this.isEmpty()) return Grid(0)

    val list = this
    return Grid<T>(list[0].size).apply {
        list.forEach {
            addAll(it)
        }
    }
}