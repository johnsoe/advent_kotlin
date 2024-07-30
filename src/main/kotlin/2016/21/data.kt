package `2016`.`21`

sealed class ScrambleOperation {
    data class SwapByIndex(val a: Int, val b: Int) : ScrambleOperation()
    data class SwapByValue(val a: Char, val b: Char) : ScrambleOperation()
    data class RotateRight(val num: Int) : ScrambleOperation()
    data class RotateLeft(val num: Int) : ScrambleOperation()
    data class RotateByValueRight(val a: Char) : ScrambleOperation()
    data class RotateByValueLeft(val a: Char) : ScrambleOperation()
    data class ReverseSubstring(val a: Int, val b: Int) : ScrambleOperation()
    data class MoveByIndex(val a: Int, val b: Int) : ScrambleOperation()
}