package twentyone

import InputParser
import LegacyInputParser

object Four : LegacyInputParser<Int>() {
     override fun getFileName() = "four.txt"

     private const val SIZE = 5

     override fun partOne(): Int {
//         val boards = generateBoards()
//         getInputByLine().first().split(",").map { it.toInt() }.forEach { num ->
//             boards.forEach { board ->
//                 board.updateFirstOfValue(num, -1)
//                 if (board.isWinner()) {
//                     return board.scoreBoard() * num
//                 }
//             }
//         }
         return 0
     }

//    private fun generateBoards(): List<BingoBoard> {
//        return getInputByChunk().drop(1).map { chunk ->
//            BingoBoard(
//                SIZE, chunk.split(" ")
//                .filter { it.isNotEmpty() }
//                .map { it.toInt() }
//            )
//        }
//    }

    override fun partTwo(): Int {
//         var boards = generateBoards()
//         getInputByLine().first().split(",").map { it.toInt() }.forEach { num ->
//             boards.forEach { board ->
//                 board.updateFirstOfValue(num, -1)
//             }
//             if (boards.size == 1 && boards.first().isWinner()) {
//                 return boards.first().scoreBoard() * num
//             }
//             boards = boards.filter { !it.isWinner() }
//         }
         return 0
     }

//     private class BingoBoard(
//         width: Int,
//         board: List<Int>
//     ) : Grid<Int>(width, board.toMutableList()) {
//
//         fun isSectionWinner(section: List<Int>) = section.all { it == -1 }
//
//         fun isWinner(): Boolean {
//             for (i in 0 until width) {
//                 if (isSectionWinner(getNthRow(i)) || isSectionWinner(getNthCol(i))) {
//                     return true
//                 }
//             }
//             return false
//         }
//
//         fun scoreBoard() = internalData.filter { it != -1 }.sum()
//         override fun toString() = internalData.toString()
//     }
 }

fun main() {
    println(Four.partOne())
    println(Four.partTwo())
}