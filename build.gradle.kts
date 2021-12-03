import org.gradle.model.internal.core.ModelNodes.withType

apply(plugin = "com.github.ben-manes.versions")

buildscript {
    val benMavenVersion  = "0.39.0"
    val gradle = Versions.gradle
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
        classpath ("de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5}")
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        useIR = true
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.Experimental"
        )
        jvmTarget = "1.8"
    }
}
