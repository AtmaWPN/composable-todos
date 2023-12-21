plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.testapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testapplication"
        minSdk = 29
        targetSdk = 33
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Room
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    val nav_version = "2.7.5"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Preferences DataStore (SharedPreferences like APIs)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // optional - RxJava2 support
    implementation("androidx.datastore:datastore-preferences-rxjava2:1.0.0")

    // optional - RxJava3 support
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.0.0")

    // for bottom navigation
    implementation("androidx.compose.material:material:1.5.4")
}