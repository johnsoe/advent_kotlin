package util.list

fun <T> Array<T>.rotate(offset: Int): Array<T> {
    val temp = this.clone()
    this.forEachIndexed { index, i ->
        var nI = index - offset
        if (nI < 0) nI += this.size
        this[index] = temp[nI % this.size]
    }
    return this
}

fun <T> Array<T>.swapValues(a: T, b: T): Array<T> {
    val aI = this.indexOf(a)
    val bI = this.indexOf(b)

    return if (aI == -1 || bI == -1) {
        this
    } else {
        swapIndices(aI, bI)
    }
}

fun <T> Array<T>.swapIndices(a: Int, b: Int): Array<T> {
    assert(a >= 0 && a < this.size)
    assert(a >= 0 && a < this.size)

    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
    return this
}

fun <T> Array<T>.contentString() = "[${this.joinToString(", ")}]"
fun <T> Array<T>.shortContentString() = this.joinToString("")
