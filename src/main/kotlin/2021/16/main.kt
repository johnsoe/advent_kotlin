package `2021`.`16`

import util.InputParser
import util.math.mult
import java.lang.IllegalStateException
import kotlin.Int

val inputParser = InputParser("2021/16/input.txt")
var versionSum = 0L

fun partOne(): Long {
    val bitString = inputParser.lines().first().hexToBitString()
    evaluatePacket(0, bitString).value
    return versionSum
}

fun evaluatePacket(
    startIndex: Int,
    bitString: String
): SubPacketResult {
    val version = bitString.slice(startIndex..startIndex+2).toInt(2)
    versionSum += version
    val typeId = TypeId.mapToType(bitString.slice(startIndex+3..startIndex+5).toInt(2))
    return when (typeId) {
        TypeId.LITERAL -> parseLiteralSubPacket(startIndex+6, bitString)
        else -> {
            when (bitString[startIndex+6]) {
                '0' -> parseLengthIdZero(startIndex+7, bitString, typeId)
                '1' -> parseLengthIdOne(startIndex+7, bitString, typeId)
                else -> throw IllegalStateException()
            }
        }
    }
}

fun parseLengthIdZero(startIndex: Int, bitString: String, operation: TypeId): SubPacketResult {
    val subPacketSize = bitString.slice(startIndex..startIndex+14).toInt(2)
    var index = startIndex + 15
    val results = mutableListOf<Long>()
    while (index < startIndex + subPacketSize + 15) {
        val result = evaluatePacket(index, bitString)
        results.add(result.value)
        index = result.index
    }
    return SubPacketResult(index, operate(operation, results))
}

fun parseLengthIdOne(startIndex: Int, bitString: String, operation: TypeId): SubPacketResult {
    val subPacketCount = bitString.slice(startIndex..startIndex+10).toInt(2)
    var index = startIndex + 11
    val results = mutableListOf<Long>()
    for (i in 0 until subPacketCount) {
        val packet = evaluatePacket(index, bitString)
        results.add(packet.value)
        index = packet.index
    }
    return SubPacketResult(index, operate(operation, results))
}

fun parseLiteralSubPacket(startIndex: Int, bitString: String): SubPacketResult {
    var index = startIndex
    var combinedBitString = ""
    var isNotTerminal = true
    while (isNotTerminal) {
        isNotTerminal = bitString[index] == '1'
        combinedBitString += bitString.slice(index+1..index+4)
        index += 5
    }
    return SubPacketResult(index, combinedBitString.toLong(2))
}

fun operate(typeId: TypeId, values: List<Long>): Long  {
    return when (typeId) {
        TypeId.SUM -> values.sum()
        TypeId.PRODUCT -> values.mult()
        TypeId.MIN -> values.minOrNull() ?: 0
        TypeId.MAX -> values.maxOrNull() ?: 0
        TypeId.GT -> if (values.first() > values.last()) 1 else 0
        TypeId.LT -> if (values.last() > values.first()) 1 else 0
        TypeId.EQ -> if (values.first() == values.last()) 1 else 0
        else -> 0
    }
}

fun partTwo(): Long {
    val bitString = inputParser.lines().first().hexToBitString()
    return evaluatePacket(0, bitString).value
}


fun String.hexToBitString(): String {
    return this.uppercase().map {
        when (it) {
            '0' -> "0000"
            '1' -> "0001"
            '2' -> "0010"
            '3' -> "0011"
            '4' -> "0100"
            '5' -> "0101"
            '6' -> "0110"
            '7' -> "0111"
            '8' -> "1000"
            '9' -> "1001"
            'A' -> "1010"
            'B' -> "1011"
            'C' -> "1100"
            'D' -> "1101"
            'E' -> "1110"
            'F' -> "1111"
            else -> throw IllegalStateException()
        }
    }.joinToString("")
}

data class SubPacketResult(
    val index: Int,
    val value: Long
)

fun main() {
    println(partOne())
    println(partTwo())
}
