// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ZephirErrorHighlightingTest : BasePlatformTestCase() {
    /** Each error is marked where the code breaks, and the valid code after it carries no mark. */
    fun testRecoveredErrors() {
        myFixture.configureByFile("RecoveredErrors.zep")
        myFixture.checkHighlighting(false, false, false)
    }

    override fun getTestDataPath() = "src/test/testData/highlighting"
}
