package `2019`.opcode

class Computer(
    val instructions: IntArray,
) {

    fun evaluate(): Int {
        var opIndex = 0
        do {
            val code = instructions[opIndex]
            val opCode = createOpCode(code, opIndex)
            opCode.execute(instructions)
            opIndex = opCode.getNextIndex(opIndex)
        } while (opCode != OpCode.Terminate)
        return instructions[0]
    }

    private fun createOpCode(code: Int, index: Int): OpCode {
        return when (code) {
            1 -> OpCode.Add(instructions.slice(index + 1..index + 3).toIntArray())
            2 -> OpCode.Multiply(instructions.slice(index + 1..index + 3).toIntArray())
            99 -> OpCode.Terminate
            else -> {
                throw IllegalStateException("$code is not a supported operation")
            }
        }
    }
}
