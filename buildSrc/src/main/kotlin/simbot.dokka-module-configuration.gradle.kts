/*
 *     Copyright (c) 2022-2024. ForteScarlet.
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

import org.jetbrains.dokka.DokkaConfiguration
import java.net.URI
import java.net.URL


plugins {
    id("org.jetbrains.dokka")
}

tasks.named("dokkaHtml").configure {
    tasks.findByName("kaptKotlin")?.also { kaptKotlinTask ->
        dependsOn(kaptKotlinTask)
    }
}
tasks.named("dokkaHtmlPartial").configure {
    tasks.findByName("kaptKotlin")?.also { kaptKotlinTask ->
        dependsOn(kaptKotlinTask)
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        version = P.Simbot.version
        documentedVisibilities.set(
            listOf(
                DokkaConfiguration.Visibility.PUBLIC,
                DokkaConfiguration.Visibility.PROTECTED
            )
        )
        jdkVersion.set(8)
        if (project.file("Module.md").exists()) {
            includes.from("Module.md")
        } else if (project.file("README.md").exists()) {
            includes.from("README.md")
        }


        sourceLink {
            localDirectory.set(projectDir.resolve("src"))
            val relativeTo = projectDir.relativeTo(rootProject.projectDir)
            remoteUrl.set(URL("${P.HOMEPAGE}/tree/v3-dev/$relativeTo/src"))
            remoteLineSuffix.set("#L")
        }

        perPackageOption {
            matchingRegex.set(".*internal.*") // will match all .internal packages and sub-packages
            suppress.set(true)
        }

        fun externalDocumentation(docUrl: URI, suffix: String = "package-list") {
            externalDocumentationLink {
                url.set(docUrl.toURL())
                packageListUrl.set(docUrl.resolve(docUrl.path).resolve(suffix).toURL())
            }
        }

        // kotlin-coroutines doc
        externalDocumentation(URI.create("https://kotlinlang.org/api/kotlinx.coroutines"))

        // kotlin-serialization doc
        externalDocumentation(URI.create("https://kotlinlang.org/api/kotlinx.serialization"))

        // SLF4J
        // externalDocumentation(URL("https://www.slf4j.org/apidocs"))

        // Spring Framework
        externalDocumentation(
            URI.create("https://docs.spring.io/spring-framework/docs/current/javadoc-api"),
            "element-list"
        )

        // Spring Boot
        externalDocumentation(URI.create("https://docs.spring.io/spring-boot/docs/current/api/element-list"))


    }
}

