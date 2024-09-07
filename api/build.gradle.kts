plugins {
    id("java")
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("com.google.guava:guava:33.3.0-jre")
    implementation("com.typesafe:config:1.4.2")
}

tasks.test {
    useJUnitPlatform()
}