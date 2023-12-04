import util.InputParser
import kotlin.Int

val inputParser = InputParser("2021/19/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val points = inputParser.chunk(",").map {
        it.split(",")
            .chunked(3)
            .map { (x, y ,z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
    }
    println(points)
    return 0
}

fun partTwo(): Int {
    println("partTwo")
    return 0
}

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int
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
