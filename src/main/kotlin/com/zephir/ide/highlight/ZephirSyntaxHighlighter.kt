// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.highlight

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.zephir.ide.color.ZephirColor
import com.zephir.lang.core.lexer.ZephirLexerAdapter
import com.zephir.lang.core.psi.ZEPHIR_DATA_TYPES
import com.zephir.lang.core.psi.ZEPHIR_KEYWORDS
import com.zephir.lang.core.psi.ZEPHIR_OPERATORS
import com.zephir.lang.core.psi.ZephirTypes.*

class ZephirSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        pack(map(tokenType)?.textAttributesKey)

    override fun getHighlightingLexer() = ZephirLexerAdapter()

    companion object {
        fun map(tokenType: IElementType?): ZephirColor? =
            when (tokenType) {
                IDENTIFIER -> ZephirColor.IDENTIFIER
                BRACKET_OPEN, BRACKET_CLOSE -> ZephirColor.BRACES
                SBRACKET_OPEN, SBRACKET_CLOSE -> ZephirColor.BRACKETS
                PARENTHESES_OPEN, PARENTHESES_CLOSE -> ZephirColor.PARENTHESIS
                DOTCOMMA -> ZephirColor.SEMICOLON
                COMMA -> ZephirColor.COMMA
                DOUBLECOLON -> ZephirColor.COLON
                INTEGER, DOUBLE -> ZephirColor.NUMBER
                STRING, SCHAR -> ZephirColor.STRING
                COMMENT -> ZephirColor.LINE_COMMENT
                COMMENT_BLOCK -> ZephirColor.BLOCK_COMMENT
                CBLOCK -> ZephirColor.C_BLOCK
                in ZEPHIR_OPERATORS -> ZephirColor.OPERATOR
                in ZEPHIR_KEYWORDS -> ZephirColor.KEYWORD
                in ZEPHIR_DATA_TYPES -> ZephirColor.DATA_TYPE
                // TODO(serghei): Do we need this? Also see com.zephir.ide.color.ZephirColor
                // TokenType.BAD_CHARACTER -> ZephirColor.BAD_CHAR
                else -> null
            }
    }
}
