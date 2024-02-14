import kotlin.Int
import util.InputParser

val inputParser = InputParser("2016/04/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return createRooms()
        .filter { it.isValid() }
        .sumOf { it.id }
}

fun createRooms(): List<Room> {
    return inputParser.lines()
        .map { line ->
            val split = line.split("-", "[", "]").dropLast(1)
            val code = split.dropLast(2).joinToString("")
            val checksum = split.last()
            val id = split[split.size - 2]
            Room(code, checksum, id.toInt())
        }
}

data class Room(
    val code: String,
    val checksum: String,
    val id: Int
) {

    fun isValid(): Boolean {
        val charMap = code
            .groupingBy { it }
            .eachCount()
        // Sort by character count, and on tie break, alphabetical order
        val checksumCompare = charMap.keys.sortedWith(
            compareByDescending<Char> { charMap[it] }.thenBy { it }
        ).take(5).joinToString("")

        return checksumCompare == checksum
    }
}

fun partTwo(): Int {
    return createRooms().map { room ->
        room.code.map {
            val shifted = (it.code - 97 + room.id) % 26
            (shifted + 97).toChar()
        }.joinToString("") to room.id
    }.first { (code, _) ->
        code.contains("northpole")
    }.second
}
