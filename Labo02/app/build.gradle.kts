plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Nota: Si el plugin de compose te da error, asegúrate de que esté
    // definido en el libs.versions.toml compatible con Kotlin 1.9.x
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.labo02luisguzman"
    // CAMBIO: Bajamos a 35 porque tu IDE no soporta las herramientas de la 36 todavía
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.labo02luisguzman"
        minSdk = 26 // Bajado un poco para mayor compatibilidad de dispositivos, pero 29 está bien si lo prefieres
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        // CAMBIO: Subimos a 17, que es el estándar actual para Compose moderno
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        // CAMBIO: Debe coincidir con el targetCompatibility
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    // Añadimos esto por si acaso el plugin de compose necesita la configuración manual
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Versión estable para Kotlin 1.9.24
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}