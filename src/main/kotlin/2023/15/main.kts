import kotlin.Int
import util.InputParser

val inputParser = InputParser("2023/15/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    return inputParser.lines()
        .first()
        .split(",")
        .sumOf { it.hashString() }
}

fun String.hashString(): Int {
    return this.toCharArray()
        .fold(0) { acc, c ->
            ((acc + c.code) * 17) % 256
        }
}

fun partTwo(): Long {
    val boxes = Array<LinkedHashMap<String, Long>>(256) {
        LinkedHashMap()
    }
    inputParser.lines().first()
        .split(",")
        .forEach {
            if (it.contains('=')) {
                val label = it.split("=").first()
                val num = it.last().code - 48
                val box = label.hashString()
                boxes[box][label] = num.toLong()
            } else {
                val label = it.split("-").first()
                val box = label.hashString()
                if (boxes[box].contains(label)) {
                    boxes[box].remove(label)
                }
            }
        }
    return boxes.mapIndexed { index, linkedHashMap ->
        linkedHashMap.values.mapIndexed { slot, num ->
            (index + 1) * (slot + 1) * num
        }.sum()
    }.sum()
}
