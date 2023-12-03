package util

import InputParser
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

public fun main(args: Array<String>) {

    val day = args.first()
    val year = args.last()
    val inputFileName = "input.txt"
    val parentDir = Paths.get("").toAbsolutePath()
    val fullPath = "$parentDir/src/main/kotlin/$year/$day"

    val adventSession: String = System.getenv("session") ?: ""

    //TODO: pull in data from site and write to this file
    println("$fullPath/$inputFileName")
    Files.createDirectory(Paths.get(fullPath))

    File("$fullPath/$inputFileName").createNewFile()
    File("$fullPath/README.md").createNewFile()

    /**
     * val inputParser = InputParser("$year/$day/input.txt")
     * println(partOne())
     * println(partTwo())
     *
     * fun partOne(): Int {
     *     return 0
     * }
     *
     * fun partTwo(): Int {
     *     return 0
     * }
     */
    val poetFile = FileSpec.scriptBuilder("main")
        .indent("    ")
        .addStatement("val inputParser = InputParser(\"$year/$day/input.txt\")")
        .addStatement("println(partOne())")
        .addStatement("println(partTwo())")
        .addStatement("")
        .addFunction(addTaskFunction("partOne"))
        .addStatement("")
        .addFunction(addTaskFunction("partTwo"))
        .build()

    poetFile.writeTo(File(fullPath))

    val mainFile = File("$fullPath/main.kts")
    mainFile.writeText(mainFile.readText().replace("public ", ""))
}

private fun addTaskFunction(name: String): FunSpec {
    return FunSpec.builder(name)
        .returns(Int::class)
        .addStatement("println(%S)", name)
        .addStatement("return 0")
        .build()
}

// TODO:
private fun makeDailyRequests() {
    //val client = HttpClient()
}
