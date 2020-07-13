// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.providers

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.PlatformIcons
import com.intellij.util.ProcessingContext
import com.zephir.lang.core.completion.ZephirCompletionProvider

/**
 * Provides code completion support at the top level.
 *
 * TODO(serghei): Add support for "as", "extends", "implements"
 */
object ZephirFileScopeCompletionProvider : ZephirCompletionProvider() {
    private val topLevel = mapOf(
        "namespace" to PlatformIcons.PACKAGE_ICON
    )

    private val keywords = arrayOf(
        "internal",
        "inline",
        "use",
        "interface",
        "class",
        "final",
        "abstract",
        "function"
    )

    override val context: ElementPattern<PsiElement>
        get() = psiElement()
            .with(object : PatternCondition<PsiElement>("toplevel") {
                override fun accepts(elem: PsiElement, context: ProcessingContext?) = !isTopStatement(elem)
            })

    override fun addCompletions(
        parameters: CompletionParameters,
        processingContext: ProcessingContext,
        result: CompletionResultSet
    ) {
        topLevel.forEach {
            result.addElement(LookupElementBuilder
                .create(it.key)
                .withIcon(it.value)
            )
        }

        keywords.forEach {
            result.addElement(LookupElementBuilder.create(it))
        }
    }
}
