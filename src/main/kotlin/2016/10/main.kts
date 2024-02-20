import `2016`.`10`.BalanceBot
import `2016`.`10`.ChipReceiver
import `2016`.`10`.OutputChannel
import kotlin.Int
import util.InputParser
import java.util.LinkedList
import java.util.Queue

val inputParser = InputParser("2016/10/input.txt")
val outputMap = mutableMapOf<Int, OutputChannel>()
val botMap = mutableMapOf<Int, BalanceBot>()
generateInstructionMaps()
processInstructions()

println(partOne())
println(partTwo())


fun partOne(): Int {
    return botMap.values.find {
        it.comparisons.first().first == 17 && it.comparisons.first().second == 61
    }?.id ?: 0
}

private fun generateInstructionMaps() {
    inputParser.linesOfType<String>()
        .forEach {
            when (it.first()) {
                "bot" -> {
                    val lowOutput = it.checkBotInput(6)
                    val highOutput = it.checkBotInput(11)
                    val id = it[1].toInt()
                    botMap.putIfAbsent(id, BalanceBot(id))
                    botMap[id]?.setOutputs(lowOutput to highOutput)
                }
                "value" -> {
                    val botId = it[5].toInt()
                    val chip = it[1].toInt()
                    botMap.putIfAbsent(botId, BalanceBot(botId))
                    botMap[botId]?.acceptChip(chip)
                }
            }
        }
}

private fun processInstructions() {
    val botActionQueue: Queue<Int> = LinkedList()
    val actionableBotIds = botMap.keys.filter { botMap[it]?.canProceed() == true }
    botActionQueue.addAll(actionableBotIds)
    while (botActionQueue.isNotEmpty()) {
        val actionId = botActionQueue.remove()
        botMap[actionId]?.let { bot ->
            bot.dispense()
            val updatedBotIds = bot.bots.toList()
                .filter { it is BalanceBot && it.canProceed() }
                .map { (it as BalanceBot).id }
            botActionQueue.addAll(updatedBotIds)
        }
    }
}

private fun List<String>.checkBotInput(index: Int): ChipReceiver {
    val id = this[index].toInt()
    return if (this[index - 1] == "bot") {
        botMap.putIfAbsent(id, BalanceBot(id))
        botMap[id]
    } else {
        outputMap.putIfAbsent(id, OutputChannel(id))
        outputMap[id]
    } as ChipReceiver
}

fun partTwo(): Int {
    return outputMap[0]!!.chip * outputMap[1]!!.chip * outputMap[2]!!.chip
}
