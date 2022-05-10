plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "org.bromles"
version = "1.0-SNAPSHOT"

kotlin {
    targets {
        android()
        jvm("desktop") {
            compilations.all {
                kotlinOptions.jvmTarget = "11"
            }
        }
        js(IR) {
            browser()
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.1")
            }
        }
        val desktopMain by getting
        val desktopTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}