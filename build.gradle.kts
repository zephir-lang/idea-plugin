// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    idea
    id("net.saliman.properties") version "1.5.2"
    id("org.jetbrains.intellij.platform") version "2.13.1"
    id("org.jetbrains.grammarkit") version "2022.3.2"
    kotlin("jvm") version "2.1.0"
}

group = "com.zephir"
version = prop("version")
description = prop("pluginDescription")

repositories {
    // Local Maven repo for IDE artifacts that are too large to download reliably.
    // Populated manually via: ~/temp/local-maven. Remove once CI can download reliably.
    val localMaven = File(System.getProperty("user.home"), "temp/local-maven")
    if (localMaven.exists()) {
        maven { url = uri(localMaven.toURI()) }
    }
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.opentest4j:opentest4j:1.3.0")
    // Grammar-kit 2022.3.2 uses these Kotlin runtime libraries at generation time.
    // The IntelliJ 2022.3 platform Maven modules (pulled via intellijRelease) do not
    // declare them as transitive deps, so we add them explicitly to the tool classpath.
    "grammarKitClassPath"("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
    "grammarKitClassPath"("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.5")

    intellijPlatform {
        intellijIdeaCommunity(prop("ideVersion"))
        testFramework(TestFrameworkType.Platform)
    }
}

idea {
    module {
        generatedSourceDirs.add(file("gen"))
    }
}

intellijPlatform {
    pluginConfiguration {
        name = prop("pluginName")
        version = prop("version")

        ideaVersion {
            sinceBuild = "243"
        }

        changeNotes = readChangeNotes("CHANGELOG.md", prop("version"))?.let {
            """
            <b>Changes in version ${prop("version")}:</b>
            $it
            <p>
                To see full list of changes to this plugin refer to
                <a href="${prop("repository")}/blob/master/CHANGELOG.md">GitHub repo</a>.
            </p>
            """.trimIndent()
        } ?: ""
    }
}

// Use IntelliJ 2022.3 platform JARs for grammar generation — grammar-kit 2022.3.2
// was built against that SDK. Without this, the tool gets 2024.3 JARs which have
// additional runtime requirements (OpenTelemetry, etc.) that break the generator.
grammarKit {
    intellijRelease.set("223.8617.56")
}

// The IntelliJ 2022.3 platform dependency tree pulls in optional AI/spellchecker
// and remote-dev JARs that are not available in public Maven repos. Grammar-kit
// doesn't use any of these for source generation, so we exclude them.
configurations.named("grammarKitClassPath") {
    exclude(group = "com.jetbrains.infra")
    exclude(group = "ai.grazie.spell")
    exclude(group = "ai.grazie.utils")
    exclude(group = "ai.grazie.nlp")
    exclude(group = "ai.grazie.model")
    exclude(group = "ai.grazie.api")
    exclude(group = "org.roaringbitmap")
    exclude(group = "com.jetbrains.rd")
}

sourceSets {
    // Only add gen/ for generated Java sources — all other dirs are already at their defaults.
    main {
        java.srcDirs("gen")
    }
}

val generateLexer = tasks.named<GenerateLexerTask>("generateLexer") {
    group = "build setup"
    description = "Generate the Lexer"
    sourceFile.set(file("src/main/grammar/Zephir.flex"))
    targetDir.set("gen/com/zephir/lang/core/lexer/")
    targetClass.set("_ZephirLexer")
    skeleton.set(file("src/main/grammar/Lexer.skeleton"))
    purgeOldFiles.set(true)
}

val generateParser = tasks.named<GenerateParserTask>("generateParser") {
    group = "build setup"
    description = "Generate the Parser and PsiElement classes"
    sourceFile.set(file("src/main/grammar/Zephir.bnf"))
    targetRoot.set("gen")
    pathToParser.set("com/zephir/lang/core/parser/ZephirParser.java")
    pathToPsiRoot.set("com/zephir/lang/core/psi")
    purgeOldFiles.set(true)
}

tasks {
    compileKotlin {
        dependsOn(generateLexer, generateParser)
    }
    // buildSearchableOptions runs a sandboxed IDE to index plugin settings for the
    // Settings search. When using a local() IDE path it incorrectly tries to register
    // the platform a second time (Plugin 2.x bug). Safe to disable — only affects
    // whether plugin settings appear in the IDE's Settings search box.
}

kotlin {
    jvmToolchain(21)
}

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
