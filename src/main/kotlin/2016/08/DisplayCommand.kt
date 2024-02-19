package `2016`.`08`

sealed class DisplayCommand {

    data class DrawRect(
        val width: Int,
        val height: Int
    ) : DisplayCommand()
    data class ShiftRow(
        val row: Int,
        val shift: Int
    ) : DisplayCommand()
    data class ShiftCol(
        val col: Int,
        val shift: Int
    ) : DisplayCommand()
}