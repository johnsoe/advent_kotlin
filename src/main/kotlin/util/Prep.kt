package util

fun main(args: Array<String>) {


    when (args.size) {
        // Only year and session specified, backfill previous year
        2 -> {
            for (i in 1..25) {
                DayGenerator(i.toNumString(), args[0], args[1]).prepare()
            }
        }
        3 -> {
            DayGenerator(args[0], args[1], args[2]).prepare()
        }
        else -> throw UnsupportedOperationException("You wut mate?")
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