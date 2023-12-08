package util.math

fun Collection<Long>.lcm(): Long {
    return this.reduce(::lcm)
}

private fun lcm(a: Long, b: Long): Long {
    return a * (b / gcd(a, b))
}

fun Collection<Long>.gcd(): Long {
    return this.reduce(::gcd)
}

private fun gcd(aStart: Long, bStart: Long): Long {
    var a = aStart
    var b = bStart
    while (b > 0) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

fun Collection<Long>.mult(): Long {
    return this.reduce { acc, l -> acc * l }
}

fun Collection<Int>.mult(): Int {
    return this.reduce { acc, l -> acc * l }
}