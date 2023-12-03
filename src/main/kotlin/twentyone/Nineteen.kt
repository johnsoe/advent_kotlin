package twentyone

import InputParser
import LegacyInputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Nineteen : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "twentyone/input/nineteen.txt"

    public override fun partOne(): Int {
        val points = getInputByChunk(",").map {
            it.split(",")
                .chunked(3)
                .map { (x, y ,z) -> Point3D(x.toInt(), y.toInt(), z.toInt()) }
        }
        println(points)
        return 0
    }

    public override fun partTwo(): Int {
        println("partTwo")
        return 0
    }
}

private data class Point3D(
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

public fun main(): Unit {
    println(Nineteen.partOne())
    println(Nineteen.partTwo())
}
