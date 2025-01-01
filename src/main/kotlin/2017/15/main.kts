
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.Int
import util.InputParser

val inputParser = InputParser("2017/15/input.txt")
val sigDigits = 65536 // 2^16
println(partOne())
println(partTwo())

fun partOne(): Int {
    val input = inputParser.lines().map {
        it.split(" ").last().toLong()
    }
    var genA = input[0]
    var genB = input[1]
    var count = 0
    repeat(40_000_000) {
        genA = (genA * 16807L) % Int.MAX_VALUE
        genB = (genB * 48271L) % Int.MAX_VALUE
        if (genA % sigDigits == genB % sigDigits) count++
    }
    return count
}

fun partTwo(): Long {
    val input = inputParser.lines().map {
        it.split(" ").last().toLong()
    }
    return Flowable.zip(
        toFlow(input[0], 16807L, 4),
        toFlow(input[1], 48271L, 8),
        ::Pair
    ).filter { (a, b) -> a % sigDigits == b % sigDigits }
        .count()
        .blockingGet()
}

private fun toFlow(start: Long, scaling: Long, mod: Long): Flowable<Long> {
    var next = start
    var count = 0
    return Flowable.create( { emitter ->
        while (count < 5_000_000) {
            next = (next * scaling) % Int.MAX_VALUE
            if (next % mod == 0L) {
                count++
                emitter.onNext(next)
            }
        }
        emitter.onComplete()
    }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.computation())
}
