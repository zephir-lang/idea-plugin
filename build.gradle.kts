// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.grammarkit.tasks.GenerateLexer
import org.jetbrains.grammarkit.tasks.GenerateParser
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.gradle.api.internal.HasConvention

group = "com.zephir"
version = prop("version")
description = prop("pluginDescription")

repositories {
    jcenter()
}

plugins {
    idea
    id("org.jetbrains.intellij") version "0.4.21"
    id("org.jetbrains.grammarkit") version "2020.1"
    id("net.saliman.properties") version "1.5.1"
    kotlin("jvm") version "1.3.72"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

idea {
    module {
        generatedSourceDirs.add(file("gen"))
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    pluginName = prop("pluginName")
    version = prop("ideVersion")
    updateSinceUntilBuild = false
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

tasks.getByName<PatchPluginXmlTask>("patchPluginXml") {
    version(version)
    sinceBuild("191")

    changeNotes("""
        <b>Changes in version $version:</b>
        ${readChangeNotes("CHANGELOG.md", version).orEmpty()}
        <p>
            To see full list of changes to this plugin refer to
            <a href="${prop("repository")}/blob/master/CHANGELOG.md">GitHub repo</a>.
        </p>
        """.trimIndent()
    )
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

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        dependsOn(
            generateLexer,
            generateParser
        )
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


val SourceSet.kotlin: SourceDirectorySet
    get() =
        (this as HasConvention)
            .convention
            .getPlugin(KotlinSourceSet::class.java)
            .kotlin

fun prop(name: String): String =
    extra.properties[name] as? String
        ?: error("Property `$name` is not defined in gradle.properties")

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
