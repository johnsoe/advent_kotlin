package util.math

data class Vector3(
    val q: Int,
    val r: Int,
    val s: Int,
) {

    operator fun plus(other: Vector3): Vector3 {
        return Vector3(q + other.q, r + other.r, s + other.s)
    }

    operator fun minus(other: Vector3): Vector3 {
        return Vector3(q - other.q, r - other.r, s - other.s)
    }
}
