// build.gradle.kts (Top-Level)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

// Dependencias y configuraciones comunes
buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin) // Use the latest version
    }
}