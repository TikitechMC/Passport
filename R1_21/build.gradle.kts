plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.7"
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }

    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

dependencies {
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.1-SNAPSHOT")
    implementation(project(":api"))
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}