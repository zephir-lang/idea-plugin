// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import org.gradle.api.internal.HasConvention
import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // The IDEA Plugin - https://docs.gradle.org/current/userguide/idea_plugin.html
    idea
    // Kotlin support
    kotlin("jvm") version "1.3.72"
    // gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    id("org.jetbrains.intellij") version "0.4.21"
    // Grammar-Kit support - read more: https://github.com/JetBrains/Grammar-Kit
    id("org.jetbrains.grammarkit") version "2020.1"
}

// Import variables from gradle.properties file
val pluginGroup: String by project
val pluginName: String by project
val pluginVersion: String by project
val pluginSinceBuild: String by project
val pluginDescription: String by project
val pluginRepository: String by project

val platformDownloadSources: String by project
val platformVersion: String by project

val psiViewerPluginVersion: String by project

group = pluginGroup
version = pluginVersion
description = pluginDescription

val isCI = !System.getenv("CI").isNullOrBlank()

// Configure project's dependencies
repositories {
    jcenter()
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName = pluginName
    version = platformVersion
    downloadSources = platformDownloadSources.toBoolean() && isCI
    updateSinceUntilBuild = true

    //  Plugin Dependencies
    val plugins = mutableListOf("java")
    if (!isCI) plugins += "PsiViewer:$psiViewerPluginVersion"

    setPlugins(*plugins.toTypedArray())
}

// Configure IDEA plugin.
idea {
    module {
        generatedSourceDirs.add(file("gen"))
    }
}

sourceSets {
    main {
        java.srcDirs("gen")
        kotlin.srcDirs("src/main/kotlin")
        resources.srcDirs("src/main/resources")
    }
    test {
        kotlin.srcDirs("src/test/kotlin")
        resources.srcDirs("src/test/resources")
    }
}

val generateLexer = task<GenerateLexer>("generateLexer") {
    group = "build setup"
    description = "Generate the Lexer"
    source = "src/main/grammar/Zephir.flex"
    targetDir = "gen/com/zephir/lang/core/lexer/"
    targetClass = "_ZephirLexer"
    skeleton = "src/main/grammar/Lexer.skeleton"
    purgeOldFiles = true
}

val generateParser = task<GenerateParser>("generateParser") {
    group = "build setup"
    description = "Generate the Parser and PsiElement classes"
    source = "src/main/grammar/Zephir.bnf"
    targetRoot = file("gen")
    pathToParser = "/com/zephir/lang/core/parser/ZephirParser.java"
    pathToPsiRoot = "/com/zephir/lang/core/psi"
    purgeOldFiles = true
}

tasks {
    runIde {
        jvmArgs("--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")
    }

    // Set the compatibility versions to 1.8
    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    listOf("compileKotlin", "compileTestKotlin").forEach {
        getByName<KotlinCompile>(it) {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    patchPluginXml {
        version(pluginVersion)
        sinceBuild(pluginSinceBuild)

        // Get the latest available change notes from the CHANGELOG.md file
        changeNotes("""
            <b>Changes in version $pluginVersion:</b>
            ${readChangeNotes("CHANGELOG.md", pluginVersion).orEmpty()}
            <p>
                To see full list of changes to this plugin refer to
                <a href="$pluginRepository/blob/master/CHANGELOG.md">GitHub repo</a>.
            </p>
            """.trimIndent()
        )
    }

    compileKotlin {
        dependsOn(
            generateLexer,
            generateParser
        )
    }
}

val SourceSet.kotlin: SourceDirectorySet
    get() =
        (this as HasConvention)
            .convention
            .getPlugin(KotlinSourceSet::class.java)
            .kotlin

fun readChangeNotes(pathname: String, version: String): String? {
    val changeLog = file(pathname)
    if (!changeLog.exists()) {
        return null
    }

    val lines = changeLog.readLines()
    val notes: MutableList<MutableList<String>> = mutableListOf()
    var note: MutableList<String>? = null
    var found = false

    for (line in lines) {
        if (!line.startsWith("## [$version") && !found) {
            continue
        }

        if (line.startsWith("## [") && found) {
            break
        }

        if (line.startsWith("## [$version")) {
            note = mutableListOf()
            notes.add(note)
            found = true

            continue
        }

        if (line.startsWith("- ") && found) {
            note?.add(
                line.trimStart('-', ' ').replace("`(.*?)`".toRegex()) {
                    "<code>${it.groupValues[1]}</code>"
                }
            )
        }
    }

    if (notes.size == 0) {
        return null
    }

    return notes.joinToString("</li><li>", prefix = "<ul><li>", postfix = "</li></ul>") {
        it.joinToString("</li><li>")
    }
}
