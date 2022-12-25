plugins {
    kotlin("jvm") version "1.7.21"
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
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
}

tasks {
    task("copyJarToPlugins") {
        copy {
            from("")
            into("")
        }
    }
}
