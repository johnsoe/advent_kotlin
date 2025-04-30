package `2016`.`10`

class OutputChannel(
    val id: Int,
) : ChipReceiver {

    var chip: Int = -1
        private set
    override fun acceptChip(amount: Int) {
        chip = amount
    }
}
