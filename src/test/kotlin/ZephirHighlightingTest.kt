// Copyright (c) 2014-2026 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.zephir.ide.color.ZephirColor
import com.zephir.ide.highlight.ZephirSyntaxHighlighter

class ZephirHighlightingTest : BasePlatformTestCase() {
    /** Every token of [text] mapped to its colour, whitespace left out. */
    private fun colours(text: String): List<Pair<String, ZephirColor?>> {
        val lexer = ZephirSyntaxHighlighter().highlightingLexer
        lexer.start(text)
        val result = mutableListOf<Pair<String, ZephirColor?>>()
        while (lexer.tokenType != null) {
            val type = lexer.tokenType
            val token = text.substring(lexer.tokenStart, lexer.tokenEnd)
            if (token.isNotBlank()) result.add(token to ZephirSyntaxHighlighter.map(type))
            lexer.advance()
        }
        return result
    }

    fun testNewKeywordsAreKeywords() {
        for ((token, colour) in colours("trait readonly yield require_once")) {
            assertEquals(token, ZephirColor.KEYWORD, colour)
        }
    }

    fun testKeywordsAreCaseInsensitive() {
        assertEquals(ZephirColor.KEYWORD, colours("TRAIT").single().second)
    }

    fun testNewOperatorsAreOperators() {
        for ((token, colour) in colours("=> .. ~ &= |= ^= <<= >>=")) {
            assertEquals(token, ZephirColor.OPERATOR, colour)
        }
    }

    fun testInterpolatedStringIsAString() {
        assertEquals(ZephirColor.STRING, colours("~\"Hello {name}\"").single().second)
    }

    fun testAttributeOpenHasTheAttributeColour() {
        assertEquals(ZephirColor.ATTRIBUTE, colours("#[").single().second)
    }

    fun testAttributeNamesAndBracketsGetTheAttributeColour() {
        myFixture.configureByText(
            "Attributes.zep",
            """
            namespace Test;

            #[Pure, Ns\Marker(1)]
            class Tagged
            {
                #[Final]
                public function run() { return 1; }
            }
            """.trimIndent()
        )

        val coloured = myFixture.doHighlighting()
            .filter { it.forcedTextAttributesKey == ZephirColor.ATTRIBUTE.textAttributesKey }
            .map { myFixture.editor.document.text.substring(it.startOffset, it.endOffset) }

        assertEquals(listOf("#[", "Pure", "Ns\\Marker", "]", "#[", "Final", "]"), coloured)
    }
}
