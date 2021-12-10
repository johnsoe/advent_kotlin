package util

import InputParser
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {

    val dayName = args[0]
    val inputFileName = "${dayName.lowercase()}.txt"
    val parentDir = Paths.get("").toAbsolutePath()

    //TODO: pull in data from site and write to this file
    File("$parentDir/src/input/$inputFileName").createNewFile()
    val dayClass = ClassName("tasks", dayName)
    val file = FileSpec.builder("tasks", dayName)
        .indent("    ")
        .addType(TypeSpec.objectBuilder(dayClass)
            .superclass(InputParser::class.parameterizedBy(Int::class))
            .addFunction(FunSpec.builder("getFileName")
                .returns(String::class)
                .addStatement("return %S", inputFileName)
                .addModifiers(KModifier.OVERRIDE)
                .build())
            .addFunction(addTaskFunction("first"))
            .addFunction(addTaskFunction("second"))
            .build())
        .addFunction(FunSpec.builder("main")
            .addStatement("println(%T.first())", dayClass)
            .addStatement("println(%T.second())", dayClass)
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