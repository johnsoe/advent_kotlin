package tasks

import InputParser

object Four : InputParser() {
     override fun getFileName() = "four.txt"

     private const val SIZE = 5

     override fun first(): Int {
         val boards = generateBoards()
         getInputByLine().first().split(",").map { it.toInt() }.forEach { num ->
             boards.forEach { board ->
                 board.updateForNum(num)
                 if (board.isWinner()) {
                     return board.scoreBoard() * num
                 }
             }
         }

         return 0
     }

    private fun generateBoards(): List<BingoBoard> {
        return getInputByChunk().drop(1).map { chunk ->
            BingoBoard(chunk.split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
                .toIntArray())
        }
    }

    override fun second(): Int {
         var boards = generateBoards()
         getInputByLine().first().split(",").map { it.toInt() }.forEach { num ->
             boards.forEach { board ->
                 board.updateForNum(num)
             }
             if (boards.size == 1 && boards.first().isWinner()) {
                 return boards.first().scoreBoard() * num
             }
             boards = boards.filter { !it.isWinner() }
         }
         return 0
     }

     private class BingoBoard constructor(
         private val board: IntArray
     ) {
         private fun getRow(row: Int): List<Int> {
             return board.filterIndexed { index, _ ->
                 index < (row + 1) * SIZE && index >= row * SIZE
             }
         }

         private fun getCol(col: Int): List<Int> {
             return board.filterIndexed { index, _ ->
                 index % SIZE == col
             }
         }

         // Unnecessary diagonals - need to read the prompt.
         private fun getPrimaryDiagonal(): List<Int> {
             return board.filterIndexed { index, _ ->
                 index % (SIZE + 1) == 0
             }
         }

         private fun getSecondaryDiagonal(): List<Int> {
             return board.filterIndexed { index, _ ->
                 index % (SIZE - 1) == 0 && index != 0 && index != (SIZE * SIZE - 1)
             }
         }

         fun updateForNum(update: Int) {
             val index = board.indexOf(update)
             if (index != -1) {
                 board[index] = -1
             }
         }

         fun isSectionWinner(section: List<Int>) = section.all { it == -1 }

         fun isWinner(): Boolean {
             for (i in 0 until SIZE) {
                 if (isSectionWinner(getRow(i)) || isSectionWinner(getCol(i))) {
                     return true
                 }
             }
             return false
         }

         fun scoreBoard() = board.filter { it != -1 }.sum()
         override fun toString() = board.contentToString()
     }
 }