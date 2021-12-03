object Two : InputParser() {
    override fun getFileName() = "two.txt"

    fun first(): Int {
        val reduced = getInputAsPair().reduce { acc, it ->
            acc.first + it.first to acc.second + it.second
        }
        return reduced.first * reduced.second
    }

    private fun getInputAsPair(): List<Pair<Int, Int>> {
        return getInputByLine().map {
            val instruction = it.split(" ")
            val direction = instruction.first()
            val amount = instruction.last().toInt()
            when (direction) {
                "forward" -> amount to 0
                "down" -> 0 to amount
                "up" -> 0 to -amount
                else -> 0 to 0
            }
        }
    }

    fun second(): Int {
        var aim = 0
        val reduced = getInputAsPair().fold(0 to 0) { acc, it ->
            if (it.first == 0) {
                aim += it.second
                acc
            } else {
                acc.first + it.first to acc.second + (aim * it.first)
            }
        }
        println(reduced)
        return reduced.first * reduced.second
    }
}