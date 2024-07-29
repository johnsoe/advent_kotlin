package util.math

import kotlin.math.max
import kotlin.math.min

/**
 * Calculate the intersection of two ranges
 * @return null if there is no intersection
 */
fun IntRange.intersection(other: IntRange): IntRange? {
    val end = min(this.last, other.last)
    val start = max(this.first, other.first)
    return if (start < end) {
        IntRange(start, end)
    } else {
        null
    }
}

/**
 * Calculate the intersection of two ranges
 * @return null if there is no intersection
 */
fun LongRange.intersection(other: LongRange): LongRange? {
    val end = min(this.last, other.last)
    val start = max(this.first, other.first)
    return if (start < end) {
        LongRange(start, end)
    } else {
        null
    }
}

/**
 * Calculate the union of two ranges
 * @return null if there is no intersection
 */
fun LongRange.union(other: LongRange): LongRange? {
    return this.intersection(other)?.let {
        LongRange(
            min(other.first, this.first),
            max(other.last, this.last)
        )
    }
}

fun LongRange.isAdjacent(other: LongRange): Boolean {
    return this.intersection(other) == null &&
            (other.first - 1 == this.last) ||
            (this.first - 1 == other.last)
}