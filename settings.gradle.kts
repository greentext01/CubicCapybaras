pluginManagement {
    repositories {
        mavenCentral()
        maven("https://repo.xenondevs.xyz/releases")
        mavenLocal { content { includeGroup("org.spigotmc") } }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
            version("kotlin", "1.7.21")

            library("spigot", "org.spigotmc", "spigot").versionRef("spigot")
            version("spigot", "1.19.3-R0.1-SNAPSHOT")
        }
    }
}

rootProject.name = "CubicCapybaras"
