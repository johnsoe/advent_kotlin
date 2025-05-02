package util.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Paths

class DayGenerator(
    private val day: String,
    private val year: String,
    private val session: String,
) {

    private val parentDir = Paths.get("").toAbsolutePath()
    private val basePath = "$parentDir/src/main/kotlin"
    private val shortPath = "$basePath/$year"
    private val fullPath = "$shortPath/$day"
    private val baseUrl = "https://adventofcode.com/$year/day/${day.toInt()}"
    private val inputUrl = "$baseUrl/input"
    private val httpClient = createHttpClient(session)

    fun prepare() {
        // Prepare main.kts file
        try {
            Files.createDirectory(Paths.get(shortPath))
        } catch (_: FileAlreadyExistsException) { }

        try {
            Files.createDirectory(Paths.get(fullPath))
        } catch (_: FileAlreadyExistsException) { }

        val mainFile = File("$fullPath/Main.kt")
        getFileSpec().writeTo(File(basePath))
        mainFile.writeText(mainFile.readText())

        val file = File("$fullPath/README.md")
        file.createNewFile()

        val inputFile = File("$fullPath/input.txt")
        inputFile.createNewFile()
        queryAdvent(httpClient, inputUrl)?.let {
            inputFile.writeText(it.string())
        }
    }

    fun populateReadme() {
        val file = File("$fullPath/README.md")
        if (!file.exists()) {
            file.createNewFile()
        }
        queryAdvent(httpClient, baseUrl)?.let {
            val responseStr = it.string()
            val startIndex = responseStr.indexOf("<article")
            val endIndex = responseStr.lastIndexOf("</article>") + 10
            file.writeText(responseStr.substring(startIndex..endIndex))
        }
    }

    /**
     * val inputParser = InputParser("$year/$day/input.txt")
     *
     * private fun partOne(): Int {
     *     return 0
     * }
     *
     * private fun partTwo(): Int {
     *     return 0
     * }
     *
     * private fun main() {
     *     println(partOne())
     *     println(partTwo())
     * }
     */
    private fun getFileSpec(): FileSpec {
        val createParser = MemberName("", "InputParser")
        return FileSpec.builder("$year.$day", "Main")
            .indent("    ")
            .addProperty(
                PropertySpec.builder("inputParser", ClassName("util", "InputParser"))
                    .addModifiers(KModifier.PRIVATE)
                    .initializer("%M(%S)", createParser, "$year/$day/input.txt")
                    .build()
            )
            .addFunction(addTaskFunction("partOne"))
            .addFunction(addTaskFunction("partTwo"))
            .addFunction(addMainFunction())
            .build()
    }

    private fun addMainFunction(): FunSpec {
        return FunSpec.builder("main")
            .addModifiers(KModifier.PRIVATE)
            .addStatement("println(partOne())")
            .addStatement("println(partTwo())")
            .build()
    }

    private fun addTaskFunction(name: String): FunSpec {
        return FunSpec.builder(name)
            .addModifiers(KModifier.PRIVATE)
            .returns(Int::class)
            .addStatement("println(%S)", name)
            .addStatement("return 0")
            .build()
    }

    private fun createHttpClient(session: String): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                Interceptor { chain: Interceptor.Chain ->
                    val original = chain.request()
                    val authorized = original.newBuilder()
                        .addHeader("Cookie", "session=$session")
                        .build()
                    chain.proceed(authorized)
                },
            )
            .build()
    }

    private fun queryAdvent(client: OkHttpClient, url: String): ResponseBody? {
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute().body
    }
}
