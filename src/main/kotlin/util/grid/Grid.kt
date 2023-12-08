package util.grid

class Grid<T>(
    val width: Int
) : ArrayList<T>() {

    public fun getAdjacentNeighbors(index: Int): Set<IndexedValue<T>> {
        return Direction.cardinalDirections().mapNotNull { direction ->
            getNeighborIndexByDirection(index, direction)?.let {
                IndexedValue(it, this[it])
            }
        }.toSet()
    }

    public fun getAllNeighbors(index: Int): Set<IndexedValue<T>> {
        return Direction.allDirections().mapNotNull { direction ->
            getNeighborIndexByDirection(index, direction)?.let {
                IndexedValue(it, this[it])
            }
        }.toSet()
    }

    private fun getNeighborIndexByDirection(index: Int, direction: Direction): Int? {
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
        return joined.chunked(width).joinToString("\n")
    }
}

public fun <T> Collection<T>.toGrid(width: Int): Grid<T> {
    return Grid<T>(width).apply { addAll(this) }
}