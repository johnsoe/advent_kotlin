package `2023`.`07`

sealed class Card(val rank: Int) {
    object Ace : Card(14)
    object King : Card(13)
    object Queen : Card(12)
    object Jack : Card(11)
    object Ten : Card(10)
    object Joker : Card(1)
    data class Numeric(val num: Int) : Card(num)

    companion object {
        fun charToCard(input: Char, allowJokers: Boolean = false): Card {
            return when (input) {
                'A' -> Ace
                'K' -> King
                'Q' -> Queen
                'J' -> if (allowJokers) Joker else Jack
                'T' -> Ten
                else -> Numeric(input.code - 48)
            }
        }
    }
}

data class Hand(
    val bid: Int,
    val cards: Array<Card>
) : Comparable<Hand> {

    private val cardCounts = cards.fold(mutableMapOf<Card, Int>()) { acc, card ->
        acc[card] = acc.getOrPut(card) { 0 } + 1
        acc
    }

    private fun getHandStrength(): Int {
        val counts = if (cards.contains(Card.Joker)) {
            val jokerCount = cardCounts[Card.Joker]!!
            val filtered = cardCounts.filter { it.key !is Card.Joker }

            // All joker hand. Hilarious edge case
            if (filtered.isEmpty()) return 7
            val maxCard = filtered.maxBy { it.value }

            filtered.keys.associate {
                if (it.rank == maxCard.key.rank) {
                    it to cardCounts[it]!! + jokerCount
                } else {
                    it to cardCounts[it]!!
                }
            }.values
        } else {
            cardCounts.values
        }

        return when {
            counts.max() == 5 -> 7 // 5 of a kind
            counts.max() == 4 -> 6 // 4 of a kind
            counts.max() == 3 && counts.min() == 2 -> 5 // full house
            counts.max() == 3 -> 4 // 3 of a kind
            counts.filter { it == 2 }.size == 2 -> 3 // Two pair
            counts.max() == 2 -> 2 // One pair
            else -> { 1 } // High hand
        }
    }

    override fun compareTo(other: Hand): Int {
        val relativeHandStrength = this.getHandStrength() - other.getHandStrength()
        if (relativeHandStrength == 0) {
            this.cards.forEachIndexed { index, card ->
                val relativeCardStrength = card.rank - other.cards[index].rank
                if (relativeCardStrength != 0) {
                    return relativeCardStrength
                }
            }
        }
        return relativeHandStrength
    }
}