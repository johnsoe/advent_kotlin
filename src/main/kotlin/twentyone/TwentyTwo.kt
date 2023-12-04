package twentyone

import util.LegacyInputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object TwentyTwo : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "twentyone/input/twentytwo.txt"

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
    println(TwentyTwo.partOne())
    println(TwentyTwo.partTwo())
}
