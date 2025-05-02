package `2015`.`13`

import util.InputParser
import kotlin.Int
import kotlin.math.max

private val inputParser = InputParser("2015/13/input.txt")
val regex = """(.+) would (.+) (\d+) happiness units by sitting next to (.+).""".toRegex()

private fun partOne(): Int {
    val members = createMemberList()
    return maximizeHappiness(mutableListOf(), members)
}

fun createMemberList(): MutableList<Member> {
    val members = mutableListOf<Member>()
    inputParser.lines().forEach { line ->
        regex.find(line)?.destructured?.toList()?.let { input ->
            val score = input[2].toInt() * if (input[1] == "lose") -1 else 1
            var member = members.find { it.name == input[0] }
            if (member == null) {
                member = Member(input[0]).apply {
                    members.add(this)
                }
            }
            member.addLink(input[3], score)
        }
    }
    return members
}

fun maximizeHappiness(selected: MutableList<Int>, members: List<Member>): Int {
    return if (selected.size == members.size) {
        calculateScore(selected, members)
    } else {
        var tempMax = 0
        (members.indices - selected.toSet()).forEach {
            selected.add(it)
            tempMax = max(tempMax, maximizeHappiness(selected, members))
            selected.removeLast()
        }
        tempMax
    }
}

fun calculateScore(selected: List<Int>, members: List<Member>): Int {
    val selectedMembers = selected.map { members[it] }
    return selectedMembers.mapIndexed { index, member ->
        when (index) {
            0 -> member.getLink(selectedMembers[selected.size - 1].name) + member.getLink(selectedMembers[1].name)
            selected.size - 1 -> member.getLink(selectedMembers[0].name) + member.getLink(selectedMembers[selected.size - 2].name)
            else -> member.getLink(selectedMembers[index - 1].name) + member.getLink(selectedMembers[index + 1].name)
        }
    }.sum()
}

private fun partTwo(): Int {
    val members = createMemberList()
    val me = Member("me").apply {
        members.forEach {
            it.addLink(this.name, 0)
            this.addLink(it.name, 0)
        }
    }
    members.add(me)
    return maximizeHappiness(mutableListOf(), members)
}

private fun main() {
    println(partOne())
    println(partTwo())
}
