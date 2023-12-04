plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "advent.ralph"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.15.2")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation(kotlin("script-runtime"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}