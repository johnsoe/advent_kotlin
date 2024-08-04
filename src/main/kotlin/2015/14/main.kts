import kotlin.Int
import util.InputParser

val inputParser = InputParser("2015/14/input.txt")
val regex = """(.*) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""".toRegex()
println(partOne())
println(partTwo())

fun partOne(): Int {
    return createReindeer().maxOf {
        it.calculateDistanceAtTime(2503)
    }
}

fun createReindeer(): List<Reindeer> {
    return inputParser.lines()
        .map {
            val lineData = regex.find(it)?.destructured?.toList() ?: emptyList()
            Reindeer(lineData[0], lineData[1].toInt(), lineData[2].toInt(), lineData[3].toInt())
        }
}

fun partTwo(): Int {
    val reindeer = createReindeer()
    // Calculate distance traveled for each reindeer, determine the winner, and count each winner
    return (1..2503).map { time ->
        val scores = reindeer.map { it.calculateDistanceAtTime(time) to it.name }
        val maxScore = scores.maxBy { it.first }.first
        scores.filter { it.first == maxScore }
            .map { it.second }
    }.flatten()
        .groupBy { it }
        .map { it.value.size }
        .max()
}

data class Reindeer (
    val name: String,
    val speed: Int,
    val activeDuration: Int,
    val inactiveDuration: Int
) {

    fun calculateDistanceAtTime(time: Int): Int {
        var completeCycles = time / (activeDuration + inactiveDuration)
        val remainingTime = time % (activeDuration + inactiveDuration)
        val extra = if (remainingTime >= activeDuration) {
            completeCycles++
            0
        } else {
            remainingTime * speed
        }
        return (completeCycles * speed * activeDuration) + extra
    }
}
