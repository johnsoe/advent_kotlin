import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.22"
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