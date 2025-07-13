import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "com.github.yuriybudiyev.utils"

    defaultConfig {
        applicationId = "com.github.yuriybudiyev.utils"
        minSdk = 26
        compileSdk = 36
        targetSdk = 36
        buildToolsVersion = "36.0.0"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.tools.desugar)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.androidx.annotation)
    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.androidx.appcompat)
    implementation(libs.bundles.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.google.material)
}
