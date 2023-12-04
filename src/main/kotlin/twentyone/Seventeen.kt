package twentyone

import util.LegacyInputParser
import java.awt.Point
import java.lang.Integer.max
import kotlin.Int
import kotlin.String
import kotlin.Unit

public object Seventeen : LegacyInputParser<Int>() {
    public override fun getFileName(): String = "twentyone/input/seventeen.txt"

    public override fun partOne(): Int {
        val input = getIntInputByLine()
        val xRange = input[0]..input[1]
        val yRange = input[2]..input[3]
        val position = Point(0, 0)
        var validYMax = 0
        var count = 0
        for (xV in 1..160) {
            for (yV in -5000..5000 ) {
                var yMax = 0
                var xPos = 0
                var xVel = xV
                for (n in 1..5000) {
                    val yPos = (yV*n) - ((n * (n - 1))/2)
                    xPos += xVel
                    xVel = max(xVel - 1, 0)
                    yMax = max(yPos, yMax)
                    if (yPos in yRange && xPos in xRange) {
                        //println("xV:$xV  yV:$yV  yMax:$yMax  xPos:$xPos yPos:$yPos n:$n")
                        count++
                        break
                    }
                }
            }
        }
        println(count)
        return 0
    }

    //Xp = Vo(n) - .5(n^2)
    //Yp = Vo(n) - .5(n^2)

    public override fun partTwo(): Int {
        println("partTwo")
        return 0
    }
}

public fun main(): Unit {
    println(Seventeen.partOne())
    println(Seventeen.partTwo())
}
