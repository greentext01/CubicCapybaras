import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version "1.7.21"
    id("xyz.xenondevs.specialsource-gradle-plugin") version "1.0.0"
    id("xyz.xenondevs.string-remapper-gradle-plugin") version "1.0.0"
}

group = "dev.oliveman"
version = "0.1"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.ticxo.modelengine:api:R3.1.3")
    compileOnly(variantOf(libs.spigot) { classifier("remapped-mojang") })
    compileOnly(libs.kotlin.stdlib)
}

tasks {
    register<Copy>("moveJar") {
        group = "build"
        dependsOn("remapObfToSpigot")
        from(File(File(project.buildDir, "libs"), "${project.name.capitalized()}-${project.version}.jar"))
        into("C:\\Users\\oaude\\Documents\\Server\\plugins\\")
    }
    named<Jar>("jar") {
        dependsOn("remapStrings")
    }
    withType<KotlinCompile>() {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

spigotRemap {
    spigotVersion.set(libs.versions.spigot.get().substringBefore("-"))
    sourceJarTask.set(tasks.jar)
    spigotJarClassifier.set("")
}

remapStrings {
    remapGoal.set("spigot")
    spigotVersion.set(libs.versions.spigot.get())
    classes.set(
        listOf(
            "dev.oliveman.cubic.capybaras.CapybaraEntity",
            "dev.oliveman.cubic.capybaras.EntitySpawnHandler"
        )
    )
}
