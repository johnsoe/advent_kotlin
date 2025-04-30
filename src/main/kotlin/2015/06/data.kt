package `2015`.`06`

import java.awt.Point

enum class LightAction {
    ON, OFF, TOGGLE
}

data class LightCommand(
    val action: LightAction,
    val start: Point,
    val end: Point,
)
