plugins {
    id("java")
}

group = "me.combimagnetron"
version = "unspecified"

repositories {
    mavenCentral()
    maven {
        name = "devOS"
        url = uri("https://mvn.devos.one/releases")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("io.github.dockyardmc:wikivg-datagen:1.3")
    implementation(project(":api"))
}

tasks.test {
    useJUnitPlatform()
}