import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.binit.zenwalls"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.binit.zenwalls"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val unsplashAccessKey = properties.getProperty("UNSPLASH_ACCESS_KEY")
        val unsplashSecretKey = properties.getProperty("UNSPLASH_SECRET_KEY")
        buildConfigField("String", "UNSPLASH_ACCESS_KEY", unsplashAccessKey)
        buildConfigField("String","UNSPLASH_SECRET_KEY",unsplashSecretKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://api.unsplash.com/\"")
        }
        debug {

            buildConfigField("String", "BASE_URL", "\"https://api.unsplash.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.ui.text.google.fonts)

    //ktor
    implementation (libs.ktor.client.android)
    implementation(libs.ktor.client.auth)
    implementation (libs.ktor.client.json)
    implementation(libs.ktor.client.cio)
    implementation (libs.ktor.client.serialization)
    implementation (libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)

    //view model
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //koin
    implementation( libs.koin.androidx.compose)
    implementation(libs.androidx.foundation.layout.android)

    //koil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}