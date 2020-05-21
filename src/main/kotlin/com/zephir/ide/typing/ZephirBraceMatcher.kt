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
import com.intellij.psi.tree.IElementType
import com.zephir.lang.core.psi.ZephirTypes

// TODO(serghei): re-visit this later to implement com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter
class ZephirBraceMatcher : PairedBraceMatcher {
    companion object {
        private val PAIRS: Array<BracePair> = arrayOf(
            BracePair(ZephirTypes.BRACKET_OPEN, ZephirTypes.BRACKET_CLOSE, true /* structural */),
            BracePair(ZephirTypes.SBRACKET_OPEN, ZephirTypes.SBRACKET_CLOSE, false),
            BracePair(ZephirTypes.PARENTHESES_OPEN, ZephirTypes.PARENTHESES_CLOSE, false),
            BracePair(ZephirTypes.LESS, ZephirTypes.GREATER, false)
        )
    }

    override fun getCodeConstructStart(file: PsiFile?, openingBraceOffset: Int): Int = openingBraceOffset
    override fun getPairs(): Array<BracePair> = PAIRS
    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true
}
