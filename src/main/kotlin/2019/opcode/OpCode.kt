package `2019`.opcode

sealed class OpCode {

    abstract fun execute(instructions: IntArray)
    abstract fun getNextIndex(index: Int): Int

    data class Add (
        val indices: IntArray
    ) : OpCode() {

        init {
            if (indices.size != 3) {
                throw IllegalStateException("Add OpCode must have 3 indices")
            }
        }

        override fun execute(instructions: IntArray) {
            val (inA, inB, out) = indices
            instructions[out] = instructions[inA] + instructions[inB]
        }

        override fun getNextIndex(index: Int) = index + indices.size + 1
    }

    data class Multiply (
        val indices: IntArray
    ) : OpCode() {

        init {
            if (indices.size != 3) {
                throw IllegalStateException("Multiply OpCode must have 3 indices")
            }
        }

        override fun execute(instructions: IntArray) {
            val (inA, inB, out) = indices
            instructions[out] = instructions[inA] * instructions[inB]
        }

        override fun getNextIndex(index: Int) = index + indices.size + 1
    }

    object Terminate : OpCode() {
        override fun execute(opcodes: IntArray) {
            // No-op
        }

        override fun getNextIndex(index: Int) = 0
    }
}