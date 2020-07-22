// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ZephirCompletionTest : BasePlatformTestCase() {
    private val testFilename get() = "${getTestName(true)}.zep"

    override fun getTestDataPath() = "src/test/testData"
    override fun getBasePath() = "completion"

    fun testFileScope() = doTest(
        "internal",
        "inline",
        "use",
        "interface",
        "class",
        "final",
        "namespace",
        "abstract",
        "function"
    )

    fun testClassScope() = doTest(
        "protected",
        "public",
        "private",
        "static",
        "inline",
        "internal",
        "final",
        "abstract",
        "function"
    )

    fun testReturnTypeHints1() = doTest(
        "void",
        "null",
        "int",
        "uint",
        "long",
        "ulong",
        "char",
        "double",
        "bool",
        "string",
        "array",
        "var",
        "callable",
        "resource",
        "object"
    )

    fun testReturnTypeHints2() = testReturnTypeHints1()

    private fun doTest(vararg variants: String) {
        myFixture.testCompletionVariants("$basePath/$testFilename", *variants)
    }
}
