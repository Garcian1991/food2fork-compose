import AndroidX.constraint_layout
import AndroidX.hilt_lifecycle_viewmodel
import AnnotationProcessing.glide_compiler
import AnnotationProcessing.hilt_compiler
import Glide.glide
import Google.hilt_android
import Square.retrofit
import Square.retrofit_gson

plugins {
    id ("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        exclude("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:${JetBrains.kotlin_stdlib}")
    implementation (AndroidX.core_ktx)

    implementation (AndroidX.app_compat)

    implementation (Google.material)

    implementation (constraint_layout)

    implementation (AndroidX.compose_ui)
    implementation (AndroidX.compose_foundation)
//    implementation ("androidx.compose.runtime:runtime-livedata:${AndroidX.compose}")
//    implementation ("androidx.compose.runtime:runtime-rxjava2:$compose_version")
    implementation (AndroidX.compose_material)
    implementation (AndroidX.compose_icons_core)
    implementation (AndroidX.compose_icons_extended)
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02")

    implementation (AndroidX.nav_fragment_ktx)
    implementation (AndroidX.nav_ui_ktx)

    implementation (retrofit)
    implementation (retrofit_gson)

    implementation (hilt_android)
    kapt (hilt_compiler)

    implementation (hilt_lifecycle_viewmodel)
    kapt (hilt_lifecycle_viewmodel)

    implementation (glide)
    kapt (glide_compiler)

}












