import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

val properties = Properties()
val propertiesFile = project.rootProject.file("local.properties") // 속성 파일 경로
properties.load(propertiesFile.inputStream())

android {
    namespace = "com.example.kuit4_android_retrofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kuit4_android_retrofit"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val baseUrl = properties["BASE_URL"]?.toString() ?: "https://default-url.com/"
        buildConfigField("String", "BASE_URL", baseUrl)
    }

    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.github.bumptech.glide:glide:4.16.0")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    val retrofit_version = "2.6.1"
// Retrofit 라이브러리
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
// Gson Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
// Scalars Converter 라이브러리
    implementation("com.squareup.retrofit2:converter-scalars:$retrofit_version")

    implementation("com.naver.maps:map-sdk:3.20.0")

    implementation("com.google.android.gms:play-services-location:21.0.1")
}
