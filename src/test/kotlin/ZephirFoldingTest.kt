// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.ide.folding.ZephirFoldingBuilder

class ZephirFoldingTest : BasePlatformTestCase() {
    fun testTraitBodyFolds() {
        myFixture.configureByText(
            "Greets.zep",
            """
            namespace Test;

            trait Greets
            {
                public function greet()
                {
                    return 1;
                }
            }
            """.trimIndent()
        )

        val text = myFixture.editor.document.text
        val descriptors = ZephirFoldingBuilder().buildFoldRegions(myFixture.file, myFixture.editor.document, false)
        val traitBody = descriptors.single { it.range.startOffset == text.indexOf('{') && it.range.endOffset > 0 &&
            text.substring(it.range.startOffset, it.range.endOffset).startsWith("{\n    public function greet") }

        assertEquals(text.lastIndexOf('}') + 1, traitBody.range.endOffset)
        assertEquals("{...}", ZephirFoldingBuilder().getPlaceholderText(traitBody.element))
    }
}
