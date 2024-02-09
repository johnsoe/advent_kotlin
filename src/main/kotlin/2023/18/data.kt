package `2023`.`18`

sealed class TrenchLine {
    abstract val range: IntRange
    abstract val coord: Int

    data class Horizontal(
        override val range: IntRange,
        override val coord: Int
    ) : TrenchLine()

    data class Vertical(
        override val range: IntRange,
        override val coord: Int
    ): TrenchLine()
}