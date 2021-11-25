apply(plugin = "com.github.ben-manes.versions")

buildscript {
    val benMavenVersion  = "0.38.0"
    val gradle = "7.0.3"
    val kotlinVersion = Versions.kotlin

    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradle")
        classpath ("com.github.ben-manes:gradle-versions-plugin:$benMavenVersion")
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}