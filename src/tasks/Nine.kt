package tasks

import InputParser
import java.util.*
import kotlin.Int
import kotlin.String

public object Nine : InputParser<Int>() {
  public override fun getFileName(): String = "nine.txt"

  public override fun first(): Int {
    val width = getInputByLine().first().length
    val chunk = getInputByChunk().joinToString("").toCharArray().map { it.toInt() - 48 }.filter { it != -16 }
    val filtered = chunk.filterIndexed { index, i ->
      val above = if (index < width) 10 else chunk[index - width]
      val below = if (index + width >= chunk.size) 10 else chunk[index + width]
      val left = if ((index % width) == 0) 10 else chunk[index - 1]
      val right = if ((index + 1) % width == 0) 10 else chunk[index + 1]
      i < above && i < left && i < right && i < below
    }
    println(filtered)
    return filtered.map { it + 1 }.sum()
  }

  public override fun second(): Int {
    val width = getInputByLine().first().length
    val chunk = getInputByChunk().joinToString("").toCharArray().map { it.toInt() - 48 }.filter { it != -16 }
    var indices = mutableSetOf<Int>()
    return chunk.mapIndexed { index, i ->
      val above = if (index < width) 10 else chunk[index - width]
      val below = if (index + width >= chunk.size) 10 else chunk[index + width]
      val left = if ((index % width) == 0) 10 else chunk[index - 1]
      val right = if ((index + 1) % width == 0) 10 else chunk[index + 1]
      if (i < above && i < left && i < right && i < below) {
        getBasinSize(chunk, width, LinkedList<Int>().apply { add(index) }, mutableSetOf())
      } else {
        1
      }
    }.sortedDescending().take(3).reduce { acc, i -> acc * i }
  }

  private fun getBasinSize(chunk: List<Int>, width: Int, toCheck: Queue<Int>, checked: MutableSet<Int>): Int {
    return if (toCheck.isEmpty()) {
      checked.size
    } else {
      val index = toCheck.remove()
      if (chunk[index] != 9 && !checked.contains(index)) {
        checked.add(index)
        if (index >= width) toCheck.add(index - width)
        if (index + width < chunk.size) toCheck.add(index + width)
        if (index % width != 0) toCheck.add(index - 1)
        if ((index + 1) % width != 0) toCheck.add(index + 1)
      }
      getBasinSize(chunk, width, toCheck, checked)
    }
  }
}
