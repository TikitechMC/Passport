plugins {
    id("java")
    `maven-publish`
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }

    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

val kotlinVersion = "1.7.22"

dependencies {
    compileOnly("com.google.guava:guava:33.3.0-jre")
    compileOnly("com.typesafe:config:1.4.2")
    compileOnly("com.github.retrooper:packetevents-spigot:2.8.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

publishing {
    repositories {
        maven {
            name = "combimagnetron"
            url = uri("https://repo.tikite.ch/releases/")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "Passport"
            version = project.version.toString()
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}