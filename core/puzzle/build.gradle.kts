import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}
val tgToken: String = localProperties.getProperty("TG_TOKEN") ?: ""
val targetAddtess: String = localProperties.getProperty("TARGET_ADDRESS") ?: ""
val minHex: String = localProperties.getProperty("MIN_HEX") ?: ""
val maxHex: String = localProperties.getProperty("MAX_HEX") ?: ""
val chatId: String = localProperties.getProperty("CHAT_ID")

android {
    namespace = "com.exxlexxlee.core.puzzle"
    compileSdk = property("version.compileSdk").toString().toInt()

    defaultConfig {
        minSdk = property("version.minSdk").toString().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "TG_TOKEN", "\"$tgToken\"")
        buildConfigField("String", "TARGET_ADDRESS", "\"$targetAddtess\"")
        buildConfigField("String", "MIN_HEX", "\"$minHex\"")
        buildConfigField("String", "MAX_HEX", "\"$maxHex\"")
        buildConfigField("Long", "CHAT_ID", chatId)
    }

    testOptions {
        targetSdk = property("version.targetSdk").toString().toInt()
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.secp256k1.kmp.jni.android)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}
