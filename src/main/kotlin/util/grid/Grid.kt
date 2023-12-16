package util.grid

import java.awt.Point

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
            getNeighborIndexByDirection(index, direction)?.let {
                IndexedValue(it, this@Grid[it])
            }
        }.toSet()
    }

    fun getNeighborIndexByDirection(index: Int, direction: Direction): Int? {
        return when (direction) {
            Direction.Up -> ::getUpNeighbor
            Direction.Down -> ::getDownNeighbor
            Direction.Left -> ::getLeftNeighbor
            Direction.Right -> ::getRightNeighbor
            Direction.UpRight -> ::getUpRightNeighbor
            Direction.DownRight -> ::getDownRightNeighbor
            Direction.UpLeft -> ::getUpLeftNeighbor
            Direction.DownLeft -> ::getDownLeftNeighbor
        }.invoke(index)
    }

    private fun getUpNeighbor(index: Int): Int? {
        return if(index in width until size) {
            index - width
        } else null
    }

    private fun getDownNeighbor(index: Int): Int? {
        return if (index + width < this.size) {
            index + width
        } else null
    }

    private fun getRightNeighbor(index: Int): Int? {
        return if ((index + 1) % width != 0) {
            index + 1
        } else null
    }

    private fun getLeftNeighbor(index: Int): Int? {
        return if (index % width != 0) {
            index - 1
        } else null
    }

    private fun getUpRightNeighbor(index: Int): Int? {
        return if (index >= width && (index + 1) % width != 0) {
            index - width + 1
        } else null
    }

    private fun getUpLeftNeighbor(index: Int): Int? {
        return if (index >= width && index % width != 0) {
            index - width - 1
        } else null
    }

    private fun getDownRightNeighbor(index: Int): Int? {
        return if (index + width < this.size && (index + 1) % width != 0) {
            index + width + 1
        } else null
    }

    private fun getDownLeftNeighbor(index: Int): Int? {
        return if (index + width < this.size && index % width != 0) {
            index + width - 1
        } else null
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
        val joined = this.joinToString("")
        return joined.chunked(width)
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

    fun getPoint(index: Int): Point {
        return Point(index % width, index / width)
    }
}

fun <T> Collection<T>.toGrid(width: Int): Grid<T> {
    val collection = this
    return Grid<T>(width).apply { addAll(collection) }
}