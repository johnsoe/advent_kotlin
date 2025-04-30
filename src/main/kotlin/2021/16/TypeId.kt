package `2021`.`16`

enum class TypeId {
    SUM,
    PRODUCT,
    MIN,
    MAX,
    LITERAL,
    GT,
    LT,
    EQ,
    ;

    companion object {
        fun mapToType(id: Int): TypeId {
            return when (id) {
                0 -> SUM
                1 -> PRODUCT
                2 -> MIN
                3 -> MAX
                4 -> LITERAL
                5 -> GT
                6 -> LT
                7 -> EQ
                else -> LITERAL
            }
        }
    }
}
