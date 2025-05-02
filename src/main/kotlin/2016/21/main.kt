package `2016`.`21`

import util.InputParser
import java.lang.UnsupportedOperationException
import kotlin.math.max
import kotlin.math.min

private val inputParser = InputParser("2016/21/input.txt")

private fun partOne(): String {
    return generateOperations().fold("abcdefgh") { acc, op ->
        acc.handleOperation(op)
    }
}

fun generateOperations(): List<ScrambleOperation> {
    return inputParser.lines().map {
        val split = it.split(" ")
        when (split[0]) {
            "move" -> ScrambleOperation.MoveByIndex(split[2].toInt(), split[5].toInt())
            "rotate" -> {
                when (split[1]) {
                    "left" -> ScrambleOperation.RotateLeft(split[2].toInt())
                    "right" -> ScrambleOperation.RotateRight(split[2].toInt())
                    "based" -> ScrambleOperation.RotateByValueRight(split[6].first())
                    else -> throw UnsupportedOperationException("Not a valid rotation command")
                }
            }
            "reverse" -> ScrambleOperation.ReverseSubstring(split[2].toInt(), split[4].toInt())
            "swap" -> {
                when (split[1]) {
                    "position" -> ScrambleOperation.SwapByIndex(split[2].toInt(), split[5].toInt())
                    "letter" -> ScrambleOperation.SwapByValue(split[2].first(), split[5].first())
                    else -> throw UnsupportedOperationException("Not a valid swap command")
                }
            }
            else -> throw UnsupportedOperationException("Not a valid command")
        }
    }
}

private fun partTwo(): String {
    return generateOperations()
        .map {
            // Reverse each operation
            when (it) {
                is ScrambleOperation.RotateLeft -> ScrambleOperation.RotateRight(it.num)
                is ScrambleOperation.RotateRight -> ScrambleOperation.RotateLeft(it.num)
                is ScrambleOperation.RotateByValueRight -> ScrambleOperation.RotateByValueLeft(it.a)
                is ScrambleOperation.MoveByIndex -> ScrambleOperation.MoveByIndex(it.b, it.a)
                else -> it
            }
        }
        .reversed()
        .fold("fbgdceah") { acc, op ->
            acc.handleOperation(op)
        }
}

fun String.handleOperation(op: ScrambleOperation): String {
    return when (op) {
        is ScrambleOperation.SwapByIndex -> {
            val high = max(op.a, op.b)
            val low = min(op.a, op.b)
            val front = this.substring(0, low)
            val back = this.substring(high + 1, this.length)
            val middle = this.substring(low + 1, high)
            "$front${this[high]}$middle${this[low]}$back"
        }
        is ScrambleOperation.SwapByValue -> {
            handleOperation(
                ScrambleOperation.SwapByIndex(
                    this.indexOf(op.a),
                    this.indexOf(op.b),
                ),
            )
        }
        is ScrambleOperation.RotateLeft -> {
            val rotation = op.num % this.length
            val newFront = this.substring(rotation, this.length)
            val back = this.substring(0, rotation)
            "$newFront$back"
        }
        is ScrambleOperation.RotateRight -> {
            val rotation = op.num % this.length
            val newFront = this.substring(this.length - rotation, this.length)
            val back = this.substring(0, this.length - rotation)
            "$newFront$back"
        }
        is ScrambleOperation.RotateByValueRight -> {
            val index = this.indexOf(op.a)
            val offset = if (index >= 4) 2 else 1
            handleOperation(
                ScrambleOperation.RotateRight(index + offset),
            )
        }
        // Bit of a cheat. This only works for strings length 8.
        is ScrambleOperation.RotateByValueLeft -> {
            val index = this.indexOf(op.a)
            val offset = if (index % 2 == 0) {
                index / 2 + 5
            } else {
                index / 2 + 1
            }
            handleOperation(
                ScrambleOperation.RotateLeft(offset),
            )
        }
        is ScrambleOperation.ReverseSubstring -> {
            val front = this.substring(0, op.a)
            val back = this.substring(op.b + 1, this.length)
            val middle = this.substring(op.a, op.b + 1).reversed()
            "$front$middle$back"
        }
        is ScrambleOperation.MoveByIndex -> {
            val char = this[op.a].toString()
            val removed = this.replace(char, "")
            val front = removed.substring(0, op.b)
            val back = removed.substring(op.b, removed.length)
            "$front$char$back"
        }
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
