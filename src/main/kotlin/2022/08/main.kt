package `2022`.`08`

import util.InputParser
import kotlin.Int

val inputParser = InputParser("2022/08/input.txt")

fun partOne(): Int {
    val trees = getTreeGrid()
    return trees.mapIndexed { y, row ->
        row.mapIndexed { x, tree ->
            val col = trees.map { it[x] }
            getTreeCross(row, col, x, y, tree).isVisible()
        }.count { it }
    }.sum()
}

fun getTreeGrid(): List<List<Int>> {
    return inputParser.lines()
        .map {
            it.toCharArray()
                .map { c -> c.code - 48 }
        }
}

fun partTwo(): Int {
    val trees = getTreeGrid()
    return trees.mapIndexed { y, row ->
        row.mapIndexed { x, tree ->
            val col = trees.map { it[x] }
            getTreeCross(row, col, x, y, tree).totalVisibilityCount()
        }.max()
    }.max()
}

fun getTreeCross(row: List<Int>, col: List<Int>, x: Int, y: Int, tree: Int): TreeCross {
    return TreeCross(
        tree,
        row.slice(0 until x).reversed(),
        row.slice(x + 1 until row.size),
        col.slice(0 until y).reversed(),
        col.slice(y + 1 until col.size),
    )
}

data class TreeCross(
    val tree: Int,
    val left: List<Int>,
    val right: List<Int>,
    val up: List<Int>,
    val down: List<Int>,
) {

    fun isVisible(): Boolean {
        return left.isVisible(tree) ||
            right.isVisible(tree) ||
            up.isVisible(tree) ||
            down.isVisible(tree)
    }

    private fun List<Int>.isVisible(tree: Int): Boolean {
        return this.all { tree > it }
    }

    fun totalVisibilityCount(): Int {
        return tree.visibleTreeCount(left) *
            tree.visibleTreeCount(right) *
            tree.visibleTreeCount(up) *
            tree.visibleTreeCount(down)
    }

    private fun Int.visibleTreeCount(trees: List<Int>): Int {
        return trees
            .indexOfFirst { it >= this }
            .let {
                if (it == -1) {
                    trees.size
                } else {
                    it + 1
                }
            }
    }
}

fun main() {
    println(partOne())
    println(partTwo())
}
