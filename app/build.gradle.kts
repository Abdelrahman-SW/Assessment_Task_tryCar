plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/NOTICE.txt")
    }

    namespace = "com.beapps.assessment_task_trycar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.beapps.assessment_task_trycar"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.bundles.ktor)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // for local unit testing :
    testImplementation(libs.junit)
    testImplementation(libs.io.mockk)
    testImplementation(libs.google.truth)
    testImplementation(libs.kotlinx.coroutines.test)


    //for instrumented testing :
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.io.mockk.android)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.kotlinx.coroutines.test)


    debugImplementation (libs.ui.test.manifest)

}