package util

import InputParser
import com.squareup.kotlinpoet.*
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {

    val dayName = args[0]
    val inputFileName = "${dayName.lowercase()}.txt"
    val parentDir = Paths.get("").toAbsolutePath()

    //TODO: pull in data from site and write to this file
    File("$parentDir/src/input/$inputFileName").createNewFile()

    val file = FileSpec.builder("tasks", dayName)
        .addType(TypeSpec.objectBuilder(dayName)
            .superclass(InputParser::class)
            .addFunction(FunSpec.builder("getFileName")
                .returns(String::class)
                .addStatement("return %S", inputFileName)
                .addModifiers(KModifier.OVERRIDE)
                .build())
            .addFunction(addTaskFunction("first"))
            .addFunction(addTaskFunction("second"))
            .build())
        .build()

    file.writeTo(File("$parentDir/src"))
}

private fun addTaskFunction(name: String): FunSpec {
    return FunSpec.builder(name)
        .returns(Int::class)
        .addModifiers(KModifier.OVERRIDE)
        .addStatement("println(%S)", name)
        .addStatement("return 0")
        .build()
}