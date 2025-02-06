plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.example.myapplication.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.myapplication.android"
        minSdk = 25
        //noinspection EditedTargetSdkVersion
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation(libs.hilt.android)
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    kapt(libs.hilt.compiler)
}