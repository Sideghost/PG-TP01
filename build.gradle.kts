import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
}

group = "me.guilh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation("pt.isel:CanvasLib-jvm:1.0.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}