// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.ide.typing

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.zephir.lang.core.psi.ZephirTypes.*
import com.zephir.lang.core.psi.ZEPHIR_COMMENTS

// TODO(serghei): re-visit this later to implement com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter
class ZephirBraceMatcher : PairedBraceMatcher {
    companion object {
        private val PAIRS: Array<BracePair> = arrayOf(
            BracePair(BRACKET_OPEN, BRACKET_CLOSE, true /* structural */),
            BracePair(SBRACKET_OPEN, SBRACKET_CLOSE, false),
            BracePair(PARENTHESES_OPEN, PARENTHESES_CLOSE, false),
            BracePair(LESS, GREATER, false)
        )

        private val InsertPairBraceBefore = TokenSet.orSet(
            ZEPHIR_COMMENTS,
            TokenSet.create(
                TokenType.WHITE_SPACE,
                DOTCOMMA,
                COMMA,
                PARENTHESES_CLOSE,
                SBRACKET_CLOSE,
                BRACKET_CLOSE,
                BRACKET_OPEN
            )
        )
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset
    override fun getPairs(): Array<BracePair> = PAIRS
    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) =
        contextType in InsertPairBraceBefore
}
