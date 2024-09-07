plugins {
    id("java")
}

subprojects {
    val adventureVersion = "4.17.0"

    apply(plugin = "java")
    listOf(
        "org.slf4j:slf4j-api:1.7.7",
        "net.kyori:adventure-api:$adventureVersion",
        "net.kyori:adventure-text-minimessage:$adventureVersion",
        "net.kyori:adventure-text-serializer-gson:$adventureVersion",
        "org.apache.commons:commons-lang3:3.16.0",
        "io.github.jglrxavpok.hephaistos:common:2.6.0"
    ).forEach {
        dependencies.add("implementation", it)
    }

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(22))
}