plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(AndroidX.core_ktx)
    implementation(AndroidX.app_compat)
    implementation(AndroidX.compose_constraint_layout)
    implementation(AndroidX.ui_tooling)
    implementation(AndroidX.compose_ui)
    implementation(AndroidX.compose_foundation)
    implementation(AndroidX.compose_material)
    implementation(AndroidX.compose_activity)
    implementation(AndroidX.compose_icons_core)
    implementation(AndroidX.compose_icons_extended)
    implementation(AndroidX.navigation_compose)
    implementation(AndroidX.navigation_hilt)
    implementation(AndroidX.room_runtime)
    implementation(AndroidX.room_ktx)
    implementation(AndroidX.datastore)
    implementation(AndroidX.hilt_lifecycle_viewmodel)

    implementation(Google.material)
    implementation(Google.hilt_android)

    implementation(Glide.glide)

    implementation(JetBrains.kotlin_stdlib)

    implementation(Square.retrofit)
    implementation(Square.retrofit_gson)
    implementation(Square.okHttp)
    implementation(Square.leak_canary)

    kapt(AnnotationProcessing.hilt_compiler)
    kapt(AnnotationProcessing.glide_compiler)
    kapt(AnnotationProcessing.room_compiler)

    // TESTING
//    testImplementation(UnitTest.jupiter_api)
//    testImplementation(UnitTest.jupiter_params)
//    testRuntimeOnly(UnitTest.jupiter_engine)
//
//    // Mock web server
//    testImplementation(UnitTest.mock_web_server)
//    testImplementation(UnitTest.okHttp)
//
//    androidTestImplementation( "androidx.test.ext:junit:1.1.3")
//
//    // compose testing
//    implementation(InstrumentationTest.compose_ui)
}












