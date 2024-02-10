import util.InputParser

val inputParser = InputParser("2019/01/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    return inputParser.linesLong()
        .sumOf { calcFuel(it) }
}

fun calcFuel(mass: Long): Long = mass / 3 - 2

fun partTwo(): Long {
    return inputParser.linesLong()
        .sumOf { calcIterFuel(it) }
}

private fun calcIterFuel(mass: Long) : Long {
    val fuel = calcFuel(mass)
    return if (fuel <= 0) {
        0
    } else {
        fuel + calcIterFuel(fuel)
    }
}
