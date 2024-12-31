package util.string

import util.grid.HexDirection

// Convert String of ints separated by spaces to list.
fun String.toInts(): List<Int> {
    return this.trim().split("\\s+".toRegex()).map { it.toInt() }
}

fun String.splitInHalf(): Pair<String, String> {
    val halfIndex = this.length / 2
    return this.take(halfIndex) to this.substring(halfIndex)
}

fun String.toHexDirection(): HexDirection? =
    HexDirection.directionMap()[this]