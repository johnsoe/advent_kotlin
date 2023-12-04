import util.InputParser
import java.awt.Point
import kotlin.Int

val inputParser = InputParser("2021/17/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Int {
    val input = inputParser.linesInt()
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
                xVel = Integer.max(xVel - 1, 0)
                yMax = Integer.max(yPos, yMax)
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

fun partTwo(): Int {
    println("partTwo")
    return 0
}
