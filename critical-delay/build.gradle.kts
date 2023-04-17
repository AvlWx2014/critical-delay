import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "dev.avlwx"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(17)
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(
                    "org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["kotlinx.coroutines.version"]}"
                )
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-coroutines-swing:${extra["kotlinx.coroutines.version"]}"
                )
            }
        }
        val jvmTest by getting {
            dependencies {
                kotlin("test")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "critical-delay"
            packageVersion = "1.0.0"

            windows {
                shortcut = true
                menu = true
                menuGroup = "AvlWx"
            }
        }
    }
}
