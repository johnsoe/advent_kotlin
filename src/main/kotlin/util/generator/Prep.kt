package util.generator

fun main(args: Array<String>) {
    val gens = mutableListOf<DayGenerator>()
    when (args.size) {
        // Only year and session specified, backfill previous year
        3 -> {
            for (i in 1..25) {
                gens.add(DayGenerator(i.toNumString(), args[1], args[2]))
            }
        }
        4 -> {
            gens.add(DayGenerator(args[1].toInt().toNumString(), args[2], args[3]))
        }
        else -> throw UnsupportedOperationException("You wut mate?")
    }

    when (args[0]) {
        "-pre" -> gens.forEach { it.prepare() }
        "-post" -> gens.forEach { it.populateReadme() }
        "-both" -> gens.forEach {
            it.prepare()
            it.populateReadme()
        }
    }
}

private fun Int.toNumString(): String {
    val intStr = this.toString()
    return if (intStr.length == 1) {
        "0$intStr"
    } else {
        intStr
    }
}
