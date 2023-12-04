package twentyone

import util.LegacyInputParser
import java.lang.Long.max
import java.util.*
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.math.ceil
import kotlin.math.floor

public object Eighteen : LegacyInputParser<Long>() {
    public override fun getFileName(): String = "twentyone/input/eighteen.txt"

    var index = 0
    public override fun partOne(): Long {
        val reducedTree = getInputByLine().map {
            index = 0
            createSnailTree(it)
        }.filterNotNull().reduce { acc, node ->
            val combined = acc.combine(node)
            while(true) {
                var exploadCount = 0
                while(explodeSnail(combined, combined, 0)) {
                    exploadCount++
                }
                if (!splitTree(combined) && exploadCount == 0) {
                    break
                }
            }
            combined
        }
        println(reducedTree)
        return reducedTree.calculateMagnitude()
    }

    private fun createSnailTree(snail: String): SnailNode? {
        if (index >= snail.length) return null
        val front = snail[index]
        if (front.isDigit()) {
            val temp = index
            while (snail[index].isDigit()) {
                index++
            }

            return SnailNode(snail.slice(temp until index).toInt())
        }

        val root = SnailNode()
        if (index < snail.length && snail[index] == '[') {
            index++
            root.left = createSnailTree(snail)
        }
        if (index < snail.length && snail[index] == ',') {
            index++
            root.right = createSnailTree(snail)
        }
        index++
        return root
    }

    private fun splitTree(snail: SnailNode, ): Boolean {
        if (snail.left?.shouldSplit() == true) {
            snail.left = snail.left?.split()
            return true
        }

        if (snail.left?.let { splitTree(it) } == true) {
            return true
        }

        if (snail.right?.shouldSplit() == true) {
            snail.right = snail.right?.split()
            return true
        }
        return snail.right?.let { splitTree(it) } ?: false
    }

    private fun explodeSnail(snail: SnailNode, root: SnailNode, depth: Int): Boolean {
        if (depth >= 4 && snail.isBasePair()) {
            val leaves = leavesInOrder(root)
            val leftIndex = leaves.indexOfFirst { it == snail.left }
            val rightIndex = leaves.indexOfFirst { it == snail.right }
            if (leftIndex - 1 >= 0) {
                leaves[leftIndex - 1].value = leaves[leftIndex - 1].value!! + snail.left!!.value!!
            }
            if (rightIndex < leaves.size - 1){
                leaves[rightIndex + 1].value = leaves[rightIndex + 1].value!! + snail.right!!.value!!
            }
            snail.left = null
            snail.right = null
            snail.value = 0
            return true
        }

        return snail.left?.let {
            explodeSnail(it, root, depth + 1)
        } ?: false || snail.right?.let {
            explodeSnail(it, root, depth + 1)
        } ?: false
    }

    private fun leavesInOrder(root: SnailNode): List<SnailNode> {
        val stack = Stack<SnailNode>()
        stack.add(root)
        val leafList = mutableListOf<SnailNode>()
        while (stack.isNotEmpty()) {
            val next = stack.pop()
            if (next.isLeaf()) {
                leafList.add(next)
            }
            next.right?.let { stack.add(it) }
            next.left?.let { stack.add(it) }
        }
        return leafList
    }

    public override fun partTwo(): Long {
        val trees = getInputByLine().map {
            index = 0
            createSnailTree(it)
        }.filterNotNull()

        trees.forEach { println(it) }

        var maxMag = 0L
        for (i in trees.indices) {
            for (j in trees.indices) {
                if (i != j) {
                    index = 0
                    val a = createSnailTree(trees[i].toString())
                    index = 0
                    val b = createSnailTree(trees[j].toString())
                    if (a == null || b == null) continue
                    val combined = a!!.combine(b!!)
                    println(combined)
                    while(true) {
                        var exploadCount = 0
                        while(explodeSnail(combined, combined, 0)) {
                            exploadCount++
                        }
                        val split = splitTree(combined)
                        if (!split && exploadCount == 0) {
                            break
                        }
                    }
                    val mag = combined.calculateMagnitude()
                    println("$mag $i $j $combined")
                    maxMag = max(mag, maxMag)
                    println(maxMag)
                }
            }
        }
        return maxMag
    }
}

class SnailNode(
    var left: SnailNode?,
    var right: SnailNode?,
    var value: Int?
) {

    constructor(value: Int) : this(null, null, value)
    constructor() : this(null, null, null)

    fun isLeaf(): Boolean = left == null && right == null && value != null
    fun shouldSplit(): Boolean = isLeaf() && value!! > 9
    fun isBasePair(): Boolean = left?.isLeaf() ?: false && right?.isLeaf() ?: false

    fun split(): SnailNode = SnailNode(
        SnailNode(floor(value!!/2.0).toInt()),
        SnailNode(ceil(value!!/2.0).toInt()),
        null
    )

    fun combine(other: SnailNode): SnailNode {
        return SnailNode(
            this,
            other,
            null
        )
    }

    fun calculateMagnitude(): Long {
        return if (this.isLeaf()) {
            this.value!!.toLong()
        } else {
            val leftSum = (this.left?.calculateMagnitude() ?: 0) * 3
            val rightSum = (this.right?.calculateMagnitude() ?: 0) * 2
            leftSum + rightSum
        }
    }

    override fun toString(): String {
        if (this.isLeaf()) {
            return this.value.toString()
        } else {
            return "[${this.left.toString()},${this.right.toString()}]"
        }
    }
}

public fun main(): Unit {
    //println(Eighteen.partOne())
    println(Eighteen.partTwo())
}
