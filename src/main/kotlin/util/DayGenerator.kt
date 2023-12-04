package util

import com.squareup.kotlinpoet.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val day = args[0]
    val year = args[1]
    val session = args[2]

    val parentDir = Paths.get("").toAbsolutePath()
    val fullPath = "$parentDir/src/main/kotlin/$year/$day"

    Files.createDirectory(Paths.get(fullPath))

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


    val httpClient = createHttpClient(session)
    val baseUrl = "https://adventofcode.com/$year/day/${day.toInt()}"
    val inputUrl = "$baseUrl/input"

    val file = File("$fullPath/README.md")
    file.createNewFile()
//    queryAdvent(httpClient, baseUrl)?.let {
//        file.writeText(it.string())
//    }

    val inputFile = File("$fullPath/input.txt")
    inputFile.createNewFile()
    queryAdvent(httpClient, inputUrl)?.let {
        inputFile.writeText(it.string())
    }
}

private fun addTaskFunction(name: String): FunSpec {
    return FunSpec.builder(name)
        .returns(Int::class)
        .addStatement("println(%S)", name)
        .addStatement("return 0")
        .build()
}

private fun createHttpClient(session: String): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val authorized = original.newBuilder()
                .addHeader("Cookie", "session=$session")
                .build()
            chain.proceed(authorized)
        })
        .build()
}

private fun queryAdvent(client: OkHttpClient, url: String): ResponseBody? {
    println(url)
    val request = Request.Builder()
        .url(url)
        .build()

    return client.newCall(request).execute().body
}
