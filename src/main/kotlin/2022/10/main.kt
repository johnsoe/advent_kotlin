package `2022`.`10`

import kotlin.Int
import util.InputParser

val inputParser = InputParser("2022/10/input.txt")



fun partOne(): Int {
    var signalStrength = 0
    processCommands { cycle, xReg ->
        if ((cycle + 20) % 40 == 0) {
            signalStrength += (xReg * cycle)
        }
    }
    return signalStrength
}

fun processCommands(callback: (Int, Int) -> Unit) {
    val cmds = getCommands()
    var xReg = 1

    var cycle = 0
    var count = 0
    var toProcess: Pair<Int, Cmd>? = null

    while(count < cmds.size) {
        // cycle start - schedule any necessary commands.
        cycle++
        if (toProcess == null) {
            toProcess = cmds[count].scheduleCommand(cycle)
        }

        // cycle during - capture state and send back to callback
        callback.invoke(cycle, xReg)

        // cycle end process queued cmd
        if (toProcess.first == cycle) {
            when (val cmd = toProcess.second) {
                Cmd.Noop -> {}
                is Cmd.Add -> {
                    xReg += cmd.add
                }
                else -> { }
            }
            toProcess = null
            count++
        }
    }
}

fun Cmd.scheduleCommand(cycle: Int): Pair<Int, Cmd> {
    return when (this) {
        Cmd.Noop -> cycle to this
        is Cmd.Add -> cycle + 1 to this
    }
}

fun getCommands(): List<Cmd> {
    return inputParser.lines()
        .map {
            when (it[0]) {
                'a' -> Cmd.Add(it.split(" ").last().toInt())
                'n' -> Cmd.Noop
                else -> Cmd.Noop
            }
        }
}

fun partTwo() {
    processCommands { cycle, xReg ->
        val range = xReg - 1..xReg + 1
        val crtSpot = (cycle - 1) % 40
        if (crtSpot in range) {
            print("#")
        } else {
            print(".")
        }

        if (cycle % 40 == 0) {
            println()
        }
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
