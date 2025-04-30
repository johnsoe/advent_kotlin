import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

group = "advent.ralph"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.15.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("io.reactivex.rxjava3:rxjava:3.1.10")
    implementation(kotlin("script-runtime"))
}

sourceSets {
    main {
        kotlin.srcDirs("src/main/kotlin")
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
}

ktlint {
    version.set("0.50.0") // Specify the ktlint version
    android.set(false) // Set to true if you're using Android
    outputToConsole.set(true)
    ignoreFailures.set(false)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
    }
    filter {
        exclude("**/generated/**")
        include("/src/main/kotlin/*.kt")
    }
}
