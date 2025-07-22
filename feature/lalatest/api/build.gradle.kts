plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.example.lalatest.api"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

dependencies {}