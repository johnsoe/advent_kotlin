package `2015`.`15`

import util.InputParser
import util.math.mult
import kotlin.Int
import kotlin.math.max

private val inputParser = InputParser("2015/15/input.txt")
val regex = """(.*): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex()

private fun partOne(): Int {
    val ingredients = createIngredients()
    return calculateScores(ingredients, mutableListOf(), 100)
}

fun calculateScores(ingredients: List<Ingredient>, selected: MutableList<Int>, remaining: Int, calorieTotal: Int? = null): Int {
    return if (selected.size + 1 == ingredients.size) {
        selected.add(remaining)
        val score = mutableListOf<Int>()
        (0..4).forEach { index ->
            // index of ingredient.characteristic
            val sum = List(selected.size) { sIndex ->
                (ingredients[sIndex].attributes[index]) * selected[sIndex]
            }.sum()
            score.add(max(sum, 0))
        }
        selected.removeLast()
        when (calorieTotal) {
            null,
            score.last(),
            -> score.dropLast(1).mult()
            else -> 0
        }
    } else {
        var currentMax = 0
        (0..remaining).forEach {
            selected.add(it)
            currentMax = max(currentMax, calculateScores(ingredients, selected, remaining - it, calorieTotal))
            selected.removeLast()
        }
        currentMax
    }
}

fun createIngredients(): List<Ingredient> {
    return inputParser.lines()
        .mapNotNull {
            regex.find(it)?.destructured?.toList()?.let { input ->
                Ingredient(
                    name = input[0],
                    attributes = input.drop(1)
                        .map { it.toInt() }
                        .toIntArray(),
                )
            }
        }
}

private fun partTwo(): Int {
    val ingredients = createIngredients()
    return calculateScores(
        ingredients = ingredients,
        selected = mutableListOf(),
        remaining = 100,
        calorieTotal = 500,
    )
}

class Ingredient(
    val name: String,
    val attributes: IntArray,
)

private fun main() {
    println(partOne())
    println(partTwo())
}
