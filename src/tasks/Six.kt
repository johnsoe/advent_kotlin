package tasks

import InputParser
import kotlin.Int
import kotlin.String

public object Six : InputParser<Long>() {
  public override fun getFileName(): String = "six.txt"

  public override fun first(): Long {
    var fishes = getInputBySeparator().map { it.toInt() }.toMutableList()
    repeat(80) {
      val reproCount = fishes.count { it == 0 }
      repeat(reproCount) { fishes.add(9) }
      fishes = fishes.map {
        if (it == 0) {
          6
        } else {
          it - 1
        }
      }.toMutableList()
    }
    return fishes.size.toLong()
  }

  public override fun second(): Long {
    //Efficient solution
    var fishes = getInputBySeparator().map { it.toInt() }
    var fishMap = mutableMapOf<Int, Long>()

    fishes.forEach {
      fishMap[it] = fishMap.getOrPut(it) { 0L } + 1
    }

    repeat(256) {
      val tempMap = mutableMapOf<Int, Long>()
      for (i in 7 downTo 0) {
        tempMap[i] = fishMap[i + 1] ?: 0
      }
      tempMap[6] = (tempMap[6] ?: 0) + (fishMap[0] ?: 0)
      tempMap[8] = fishMap[0] ?: 0
      fishMap = tempMap
    }
    return fishMap.values.sum()
  }
}
