package util.grid

import util.math.Vector3

// Flat top HexDirections.
sealed class HexDirection {
    object North: HexDirection()
    object NorthEast: HexDirection()
    object NorthWest: HexDirection()
    object South: HexDirection()
    object SouthEast: HexDirection()
    object SouthWest: HexDirection()

    fun toVec(): Vector3 {
        return when (this) {
            North -> Vector3(0, -1, 1)
            South -> Vector3(0, 1, -1)
            NorthEast -> Vector3(1, -1, 0)
            SouthWest -> Vector3(-1, 1, 0)
            NorthWest -> Vector3(-1, 0, 1)
            SouthEast -> Vector3(1, 0, -1)
        }
    }

    companion object {
        fun directionMap(): Map<String, HexDirection> = mapOf(
            "ne" to NorthEast,
            "nw" to NorthWest,
            "n" to North,
            "se" to SouthEast,
            "sw" to SouthWest,
            "s" to South,
        )
    }
}

