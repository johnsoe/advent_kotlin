package `2024`.`13`

import util.InputParser
import util.math.Vector2

val inputParser = InputParser("2024/13/input.txt")
val buttonRegex = """X\+(\d+), Y\+(\d+)""".toRegex()
val targetRegex = """X=(\d+), Y=(\d+)""".toRegex()

fun partOne(): Long {
    return getClawInstructions()
        .mapNotNull { it.calcPresses() }
        .sum()
}

private fun getClawInstructions(): List<Claw> {
    return inputParser.chunk().map {
        val mapped = it.map { s ->
            val inputs = buttonRegex.find(s)?.destructured?.toList()
                ?: targetRegex.find(s)?.destructured?.toList()
                ?: emptyList()
            Vector2(inputs[0].toInt(), inputs[1].toInt())
        }
        Claw(mapped[0], mapped[1], mapped[2])
    }
}

private data class Claw(
    val a: Vector2,
    val b: Vector2,
    val target: Vector2,
) {

    // Really need to make Vec2 generic.
    // Solve system of equations
    fun calcPresses(targetOffset: Long = 0): Long? {
        val targetX = target.x + targetOffset
        val targetY = target.y + targetOffset
        val tX = targetX * b.y
        val tY = targetY * b.x
        val mX: Long = a.x.toLong() * b.y
        val nX: Long = b.x.toLong() * b.y
        val mY: Long = a.y.toLong() * b.x
        val nY: Long = b.y.toLong() * b.x

        if (nX - nY != 0L) return null
        val subM = mX - mY
        val targetM = tX - tY
        if (targetM % subM != 0L) return null

        val m = targetM / subM
        val n = (targetX - (m * a.x)) / b.x

        return 3 * m + n
    }
}

fun partTwo(): Long {
    return getClawInstructions()
        .mapNotNull { it.calcPresses(10_000_000_000_000L) }
        .sum()
}

fun main() {
    println(partOne())
    println(partTwo())
}
