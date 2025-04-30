package util.hash

import okhttp3.internal.toHexString

private val LOOP_COUNT = 256

fun String.toKnotHash(): String {
    val input = this.map { it.code } + listOf(17, 31, 73, 47, 23)
    var index = 0
    val arr = IntArray(LOOP_COUNT) { it }
    repeat(64) { rC ->
        input.forEachIndexed { skip, sub ->
            arr.knotReverse(index, (index + sub) % LOOP_COUNT)
            index = (index + sub + skip + (rC * input.size)) % LOOP_COUNT
        }
    }

    return arr.toList()
        .chunked(16)
        .joinToString("") {
            it.reduce { acc, i -> acc.xor(i) }
                .toHexString()
                .padStart(2, '0')
        }
}

fun IntArray.knotReverse(from: Int, to: Int) {
    if (from == to) return
    if (from < to) {
        this.reverse(from, to)
    } else {
        val steps = (to + this.size - from) / 2
        repeat(steps) {
            val fromI = (from + it) % LOOP_COUNT
            val toI = (to - 1 - it + LOOP_COUNT) % LOOP_COUNT
            val temp = this[toI]
            this[toI] = this[fromI]
            this[fromI] = temp
        }
    }
}
