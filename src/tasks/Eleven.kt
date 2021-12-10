package tasks

import InputParser
import kotlin.Int
import kotlin.String

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
