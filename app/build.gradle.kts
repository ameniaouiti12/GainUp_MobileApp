// build.gradle.kts
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "tn.esprit.gainupdam"
    compileSdk = 35

    defaultConfig {
        applicationId = "tn.esprit.gainupdam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        viewBinding = true  // Enable ViewBinding if needed
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core AndroidX dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    // Material Icons Dependency
    implementation("com.google.android.material:material:1.5.0")

    // Facebook SDK
    implementation("com.facebook.android:facebook-android-sdk:[4,5)") // Try a different version

    //google
    implementation ("com.google.android.gms:play-services-auth:20.7.0")


    // Retrofit, OkHttp, and Dagger (Hilt) Dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.google.dagger:hilt-android:2.51")
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.security.crypto.ktx)

    // Testing Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //icon
    implementation ("androidx.compose.ui:ui:1.5.1")
    implementation ("androidx.compose.material:material:1.5.1")
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.1")


    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")


    implementation ("androidx.preference:preference-ktx:1.1.1")

    implementation ("com.google.firebase:firebase-messaging:23.0.6")
    implementation ("com.google.firebase:firebase-analytics:20.1.0")
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.4.2")
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.activity:activity-ktx:1.4.0")
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    implementation ("androidx.compose.material3:material3:1.2.0") // Replace with the latest version
    implementation ("androidx.compose.ui:ui:1.6.0")
    implementation ("androidx.compose.foundation:foundation:1.6.0")

    // Annotation Processor for Dagger (Hilt)
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation("androidx.security:security-crypto-ktx:1.1.0-alpha04")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("io.coil-kt:coil-compose:2.0.0")



}
