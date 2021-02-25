pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}


rootProject.name = "PeopleInSpace"

enableFeaturePreview("GRADLE_METADATA")
if (INCLUDE_APP == "true") {
    include(":app")
    include(":compose-desktop")
}
include(":common")
include(":web")
include(":backend")