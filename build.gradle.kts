// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
//    kotlin("jvm") version "2.0.0-RC1"
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply false
    kotlin("jvm") version "2.0.20" // or kotlin("multiplatform") or any other kotlin plugin
    kotlin("plugin.serialization") version "2.0.20"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
//    kotlin("plugin.serialization") version "1.9.0" apply false

}