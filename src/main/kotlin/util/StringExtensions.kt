package util

// Convert String of ints separated by spaces to list.
fun String.toInts(): List<Int> {
    return this.trim().split("\\s+".toRegex()).map { it.toInt() }
}