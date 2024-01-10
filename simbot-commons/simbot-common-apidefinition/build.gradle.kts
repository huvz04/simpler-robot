/*
 *     Copyright (c) 2024. ForteScarlet.
 *
 *     Project    https://github.com/simple-robot/simpler-robot
 *     Email      ForteScarlet@163.com
 *
 *     This file is part of the Simple Robot Library.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     Lesser GNU General Public License for more details.
 *
 *     You should have received a copy of the Lesser GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

import love.forte.gradle.common.core.project.setup

plugins {
    kotlin("multiplatform")
}

setup(P.Simbot)
apply(plugin = "simbot-multiplatform-maven-publish")

configJavaCompileWithModule("simbot.common.apidefinition")
apply(plugin = "simbot.dokka-module-configuration")

kotlin {
    explicitApi()
    applyDefaultHierarchyTemplate()

    configKotlinJvm(JVMConstants.KT_JVM_TARGET_VALUE)

    js(IR) {
        browser()
        nodejs()
    }

    // tier1
    linuxX64()
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    // tier2
    linuxArm64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()
    iosArm64()

    // tier3
    // androidNativeArm32()
    // androidNativeArm64()
    // androidNativeX86()
    // androidNativeX64()
    mingwX64()
    // watchosDeviceArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization.core)
            api(libs.ktor.http)
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jvmTest {
            dependencies {
                api(libs.slf4j.api)
            }
        }
    }
}

