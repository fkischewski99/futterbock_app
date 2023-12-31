plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}



kotlin {
    androidTarget()

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val voyagerVersion = "1.0.0";
                val lifecycleVersion = "2.6.2";
                val koin = "3.4.3";
                val koincompose = "1.0.4";

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
                // Drag and Drop
                implementation("com.mohamedrejeb.dnd:compose-dnd:0.1.0")

                //Moko view model
                api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
                api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multiplatform

                ///// KOIN /////
                implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.3"))
                implementation("io.insert-koin:koin-core")
                implementation("io.insert-koin:koin-compose")
                //ViewModel
                // ViewModel utilities for Compose
                //implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
                //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                // Android Studio Preview support
                //implementation("androidx.compose.ui:ui-tooling-preview")
                //implementation("androidx.compose.material3:material3:1.1.2")

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
                implementation("io.insert-koin:koin-android:3.4.3")
                //implementation("org.jetbrains.compose.ui:ui-tooling-preview")
                //implementation("androidx.compose.material3:material3:1.1.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation("org.jetbrains.compose.ui:ui-tooling-preview")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    java {
        sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
        targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
    }
}
