plugins {
    id("java")
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    implementation(project(":R1_21"))
}

tasks {
    build {
        dependsOn(":api:build")
        dependsOn(":R1_21:build")
        dependsOn("shadowJar")
    }
}

publishing {
    repositories {
        maven {
            name = "combimagnetron"
            url = uri("http://78.47.189.94/releases/")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
            isAllowInsecureProtocol = true
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "Passport"
            version = project.version.toString()
            from(components["shadow"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}