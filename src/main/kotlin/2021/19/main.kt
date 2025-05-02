package `2021`.`19`

import util.InputParser
import kotlin.Int

private val inputParser = InputParser("2021/19/input.txt")

private fun partOne(): Int {
    val points = inputParser.chunkAndJoin(",").map {
        it.split(",")
            .chunked(3)
            .map { (x, y, z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
    }
    println(points)
    return 0
}

private fun partTwo(): Int {
    return 0
}

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    fun allOrientations(): Set<Point3D> {
        return setOf(
            Point3D(x, y, z), Point3D(x, -z, y), Point3D(x, -y, -z), Point3D(x, z, -y), Point3D(-x, -y, z),
            Point3D(-x, -z, -y), Point3D(-x, y, -z), Point3D(-x, z, y), Point3D(-z, x, -y), Point3D(y, x, -z),
            Point3D(z, x, y), Point3D(-y, x, z), Point3D(z, -x, -y), Point3D(y, -x, z), Point3D(-z, -x, y),
            Point3D(-y, -x, -z), Point3D(-y, -z, x), Point3D(z, -y, x), Point3D(y, z, x), Point3D(-z, y, x),
            Point3D(z, y, -x), Point3D(-y, z, -x), Point3D(-z, -y, -x), Point3D(y, -z, -x),
        )
    }
}

private fun main() {
    println(partOne())
    println(partTwo())
    println("partTwo")
}
