package `2016`.assembunny

typealias OutputCallback = (Int) -> Boolean

object AssembunnyInterpreter {

    fun parseInstructions(
        instructions: List<List<String>>,
        registers: MutableMap<String, Int>,
        output: OutputCallback = { false },
    ) {
        val toggledInstructions = mutableSetOf<Int>()
        var instructionPtr = 0
        while (instructionPtr < instructions.size) {
            val instruction = getInstruction(instructions, instructionPtr, toggledInstructions)
            when (instruction.first()) {
                "cpy" -> {
                    val register = instruction.last()
                    if (registers[register] == null) continue
                    registers[register] = instruction[1].parseRegisterInt(registers)
                }
                "inc" -> {
                    registers.add(instruction.last(), 1)
                }
                "dec" -> {
                    registers.add(instruction.last(), -1)
                }
                "jnz" -> {
                    val jumpAmt = instruction.last().parseRegisterInt(registers)
                    if (instruction[1].parseRegisterInt(registers) != 0) {
                        instructionPtr += (jumpAmt - 1)
                    }
                }
                "tgl" -> {
                    val target = instruction.last().parseRegisterInt(registers) + instructionPtr
                    if (toggledInstructions.contains(target)) {
                        toggledInstructions.remove(target)
                    } else {
                        toggledInstructions.add(target)
                    }
                }
                "out" -> {
                    if (!output(instruction.last().parseRegisterInt(registers))) {
                        break
                    }
                }
            }
            instructionPtr++
        }
    }

    private fun getInstruction(
        instructions: List<List<String>>,
        index: Int,
        toggled: Set<Int>,
    ): List<String> {
        val instruction = instructions[index]
        return if (index !in toggled) {
            instruction
        } else {
            when (instruction.first()) {
                "cpy" -> listOf("jnz", instruction[1], instruction.last())
                "inc" -> listOf("dec", instruction.last())
                "dec" -> listOf("inc", instruction.last())
                "jnz" -> listOf("cpy", instruction[1], instruction.last())
                "tgl" -> listOf("inc", instruction.last())
                "out" -> listOf("inc", instruction.last())
                else -> listOf()
            }
        }
    }

    private fun MutableMap<String, Int>.add(key: String, toAdd: Int) {
        this[key] = this[key]?.plus(toAdd) ?: 0
    }

    private fun String.parseRegisterInt(registers: Map<String, Int>): Int {
        val amt = this.toIntOrNull()
        return amt ?: (registers[this] ?: 0)
    }
}
