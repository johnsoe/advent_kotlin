package tasks

import InputParser
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Eleven : InputParser<Int>() {
    public override fun getFileName(): String = "eleven.txt"

    public override fun first(): Int {
        println("first")
        return 0
    }

    public override fun second(): Int {
        println("second")
        return 0
    }
}

public fun main(): Unit {
    println(Eleven.first())
    println(Eleven.second())
}
