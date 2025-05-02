package util.generator

import io.github.cdimascio.dotenv.dotenv

fun main(args: Array<String>) {
    val dotenv = dotenv()
    val session = dotenv["SESSION"] ?: throw IllegalStateException("SESSION not found in .env file")
    val gens = mutableListOf<DayGenerator>()
    when (args.size) {
        // Only type and year specified, backfill previous year
        2 -> {
            for (i in 1..25) {
                gens.add(
                    DayGenerator(
                        day = i.toNumString(),
                        year = args[1],
                        session = session
                    )
                )
            }
        }
        3 -> {
            gens.add(
                DayGenerator(
                    day = args[1].toInt().toNumString(),
                    year = args[2],
                    session = session
                )
            )
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
