// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.zephir.lang.core.psi.ZephirFile

/**
 * The base class to implement Zephir completion providers.
 */
abstract class ZephirCompletionProvider : CompletionProvider<CompletionParameters>() {
    abstract val context: ElementPattern<out PsiElement>
    open val type: CompletionType = CompletionType.BASIC

    fun isTopStatement(elem: PsiElement) = elem.parent is ZephirFile

    // TODO(serghei): fun PsiElement.isOfType(elementType: IElementType, vararg rest: IElementType): Boolean
}
