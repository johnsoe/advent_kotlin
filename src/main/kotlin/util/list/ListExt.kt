package util.list

import util.grid.Grid
import util.grid.toGrid

fun List<String>.toCharGrid(): Grid<Char> =
    this.map { it.toCharArray().toList() }.toGrid()
