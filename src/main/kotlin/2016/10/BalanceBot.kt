package `2016`.`10`

class BalanceBot(val id: Int): ChipReceiver {

    lateinit var bots: Pair<ChipReceiver, ChipReceiver>
        private set

    var chips: MutableList<Int> = mutableListOf()
        private set

    val comparisons: MutableList<Pair<Int, Int>> = mutableListOf()

    fun setOutputs(bots: Pair<ChipReceiver, ChipReceiver>) {
        this.bots = bots
    }

    override fun acceptChip(amount: Int) {
        chips.add(amount)
    }

    fun canProceed(): Boolean {
        if (chips.size > 2) {
            throw IllegalStateException("Should never proceed with more than 2 values")
        }
        return chips.size == 2
    }

    fun dispense() {
        val sortedChips = chips.sorted()
        bots.first.acceptChip(sortedChips.first())
        bots.second.acceptChip(sortedChips.last())
        comparisons.add(sortedChips.first() to sortedChips.last())
        chips.clear()
    }
}