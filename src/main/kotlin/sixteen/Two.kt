package sixteen

import LegacyInputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Two : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "sixteen/input/two.txt"

    public override fun partOne(): Int {
        println("partOne")
        return 0
    }

    public override fun partTwo(): Int {
        println("partTwo")
        return 0
    }
}

public fun main(): Unit {
    println(Two.partOne())
    println(Two.partTwo())
}
