package `2018`.`04`

import kotlin.Int
import util.InputParser

private val inputParser: InputParser = InputParser("2018/04/input.txt")

/**
 * --- Day 4: Repose Record ---
 * Completed with the help of Github Copilot (1 Attempt)
 */
private fun partOne(): Int {
    val records = inputParser.lines().sorted() // Sort records chronologically
    val sleepMinutes = mutableMapOf<Int, IntArray>() // Map of guard ID to minutes asleep
    var currentGuard = 0
    var sleepStart = 0

    for (record in records) {
        when {
            "begins shift" in record -> {
                currentGuard = """Guard #(\d+)""".toRegex().find(record)!!.groupValues[1].toInt()
                sleepMinutes.putIfAbsent(currentGuard, IntArray(60))
            }
            "falls asleep" in record -> {
                sleepStart = """\d{2}:(\d{2})""".toRegex().find(record)!!.groupValues[1].toInt()
            }
            "wakes up" in record -> {
                val sleepEnd = """\d{2}:(\d{2})""".toRegex().find(record)!!.groupValues[1].toInt()
                for (minute in sleepStart until sleepEnd) {
                    sleepMinutes[currentGuard]!![minute]++
                }
            }
        }
    }

    // Find the guard with the most total minutes asleep
    val (guardId, minutes) = sleepMinutes.maxByOrNull { it.value.sum() }!!
    val mostAsleepMinute = minutes.indices.maxByOrNull { minutes[it] }!!

    return guardId * mostAsleepMinute
}

private fun partTwo(): Int {
    val records = inputParser.lines().sorted() // Sort records chronologically
    val sleepMinutes = mutableMapOf<Int, IntArray>() // Map of guard ID to minutes asleep
    var currentGuard = 0
    var sleepStart = 0

    for (record in records) {
        when {
            "begins shift" in record -> {
                currentGuard = """Guard #(\d+)""".toRegex().find(record)!!.groupValues[1].toInt()
                sleepMinutes.putIfAbsent(currentGuard, IntArray(60))
            }
            "falls asleep" in record -> {
                sleepStart = """\d{2}:(\d{2})""".toRegex().find(record)!!.groupValues[1].toInt()
            }
            "wakes up" in record -> {
                val sleepEnd = """\d{2}:(\d{2})""".toRegex().find(record)!!.groupValues[1].toInt()
                for (minute in sleepStart until sleepEnd) {
                    sleepMinutes[currentGuard]!![minute]++
                }
            }
        }
    }

    // Find the guard and minute with the highest frequency
    var maxGuard = 0
    var maxMinute = 0
    var maxFrequency = 0

    for ((guardId, minutes) in sleepMinutes) {
        for (minute in minutes.indices) {
            if (minutes[minute] > maxFrequency) {
                maxGuard = guardId
                maxMinute = minute
                maxFrequency = minutes[minute]
            }
        }
    }

    return maxGuard * maxMinute
}

private fun main() {
    println(partOne())
    println(partTwo())
}
