rootProject.name = "Passport"

dependencyResolutionManagement {
    repositories {
        mavenCentral() //
        maven(url = uri("https://jitpack.io")) //PacketEvents
        maven(url = uri("https://repo.papermc.io/repository/maven-public/")) //Paper, Paperweight, Velocity
        maven(url = uri("https://repo.spongepowered.org/repository/maven-public/")) //Configurate
    }
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include("api")
include("generator")
include("R1_21")
include("artifact")
