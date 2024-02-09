package util.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class DayGenerator (
    private val day: String,
    private val year: String,
    private val session: String,
) {
    
    private val parentDir = Paths.get("").toAbsolutePath()
    private val fullPath = "$parentDir/src/main/kotlin/$year/$day"
    private val baseUrl = "https://adventofcode.com/$year/day/${day.toInt()}"
    private val inputUrl = "$baseUrl/input"
    private val httpClient = createHttpClient(session)

    fun prepare() {
        // Prepare main.kts file
        Files.createDirectory(Paths.get(fullPath))
        getFileSpec().writeTo(File(fullPath))
        val mainFile = File("$fullPath/main.kts")
        mainFile.writeText(mainFile.readText().replace("public ", ""))

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
    private fun getFileSpec(): FileSpec {
        val createParser = MemberName("util", "InputParser")
        return FileSpec.scriptBuilder("main")
            .indent("    ")
            .addStatement("val inputParser = %M(\"$year/$day/input.txt\")", createParser)
            .addStatement("println(partOne())")
            .addStatement("println(partTwo())")
            .addStatement("")
            .addFunction(addTaskFunction("partOne"))
            .addStatement("")
            .addFunction(addTaskFunction("partTwo"))
            .build()
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
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute().body
    }
}