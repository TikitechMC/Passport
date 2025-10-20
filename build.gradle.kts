plugins {
    id("java")
}

subprojects {
    val adventureVersion = "4.19.0"

    apply(plugin = "java")
    listOf(
        "org.slf4j:slf4j-api:1.7.7",
        "net.kyori:adventure-api:$adventureVersion",
        "net.kyori:adventure-text-minimessage:$adventureVersion",
        "net.kyori:adventure-text-serializer-gson:$adventureVersion",
        "net.kyori:adventure-nbt:$adventureVersion",
    ).forEach {
        dependencies.add("implementation", it)
    }
    listOf(
        "org.apache.commons:commons-lang3:3.16.0"
    ).forEach {
        dependencies.add("compileOnly", it)
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(23))
}