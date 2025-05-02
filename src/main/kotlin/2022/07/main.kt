package `2022`.`07`

import util.InputParser

private val inputParser = InputParser("2022/07/input.txt")

private fun partOne(): Long {
    val root = generateDirectoryTree()
    val allDirs = mutableSetOf<File.Dir>()
    exploreSubdirectory(root, allDirs)

    // some inefficiencies here, as size aggregates all subdirectories.
    return allDirs
        .filter { it.size < 100000 }
        .sumOf { it.size }
}

fun exploreSubdirectory(dir: File.Dir, allDirs: MutableSet<File.Dir>) {
    allDirs.add(dir)
    dir.files.forEach {
        if (it is File.Dir) {
            exploreSubdirectory(it, allDirs)
        }
    }
}

fun generateDirectoryTree(): File.Dir {
    val root = File.Dir("/", null)
    var currentDir = root
    inputParser.lines()
        .drop(1)
        .map { it.split(" ") }
        .forEach { cmds ->
            val cmd = cmds.first()
            when {
                cmd == "$" && cmds[1] == "cd" -> currentDir = navigateTo(cmds[2], currentDir, root)
                cmd == "$" && cmds[1] == "ls" -> {}
                cmd == "dir" -> currentDir.files.add(File.Dir(name = cmds[1], parent = currentDir))
                else -> currentDir.files.add(File.Plain(size = cmds[0].toLong(), name = cmds[1]))
            }
        }
    return root
}

fun navigateTo(dir: String, currentDir: File.Dir, root: File.Dir): File.Dir {
    return when (dir) {
        ".." -> currentDir.parent!!
        "/" -> root
        else -> {
            val found = currentDir.files
                .find {
                    it.name == dir
                } ?: throw IllegalStateException("Assuming this shouldn't happen")
            found as File.Dir
        }
    }
}

private fun partTwo(): Long {
    val root = generateDirectoryTree()
    val allDirs = mutableSetOf<File.Dir>()
    exploreSubdirectory(root, allDirs)

    val limit = -40000000 + root.size
    return allDirs
        .filter { it.size > limit }
        .minOf { it.size }
}

private fun main() {
    println(partOne())
    println(partTwo())
}
