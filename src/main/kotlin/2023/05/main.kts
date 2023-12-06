import util.InputParser

val inputParser = InputParser("2023/05/input.txt")
println(partOne())
println(partTwo())

fun partOne(): Long {
    val mappings = getMappings()
    return getSeeds().minOfOrNull { initSeed ->
        mappings.fold(initSeed) { seed, ranges ->
            val validRange = ranges.find { (_, src, count) ->
                seed in src..(src + count)
            }
            validRange?.let { (dest, src, _) ->
                seed - (src - dest)
            } ?: seed
        }
    } ?: 0
}

fun getSeeds(): List<Long> {
    return inputParser.lines()
        .first()
        .split(" ")
        .drop(1)
        .map { it.toLong() }
}

fun getSeedRanges(): List<LongRange> {
    return inputParser.lines()
        .first()
        .split(" ")
        .drop(1)
        .map { it.toLong() }
        .chunked(2)
        .map { it[0] until it[0]+it[1] }
}

fun getMappings(): List<List<Triple<Long, Long, Long>>> {
    return inputParser.chunk().drop(1)
        .map {
            it.split(" ")
                .drop(2)
                .map { it.toLong() }
                .chunked(3)
                .map { instructions ->
                    Triple(instructions[0], instructions[1], instructions[2])
                }
        }
}

fun partTwo(): Long {
    val seeds = getSeedRanges()
    val mappings = getMappings()

    val max = mappings.last().maxOfOrNull { (dest, _, count) ->
        (dest..(dest + count)).last
    }!!

    (0..max).map { initSeed ->
        val result = mappings.reversed().fold(initSeed) { seed, ranges ->
            val validRange = ranges.find { (dest, _, count) ->
                seed in dest..(dest + count)
            }
            validRange?.let { (dest, src, _) ->
                seed - (dest - src)
            } ?: seed
        }
        if (seeds.any { result in it }) {
            return initSeed
        }
    }
    return 0
}