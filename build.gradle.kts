plugins {
    alias(libs.plugins.android.application) apply false
}

buildscript {
    dependencies {
        classpath(libs.kotlin.plugin)
    }
}
