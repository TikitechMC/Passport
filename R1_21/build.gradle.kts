plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.7.4"
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}