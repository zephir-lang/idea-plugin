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
import com.intellij.icons.AllIcons
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import com.zephir.lang.core.completion.ZephirCompletionProvider

/**
 * Provides code completion support at the top level.
 */
object ZephirFileScopeCompletionProvider : ZephirCompletionProvider() {
    // TODO(serghei): Add support for "as", "extends", "implements"
    private val candidates = arrayOf(
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
        result.addElement(LookupElementBuilder
            .create("namespace")
            .withIcon(AllIcons.Nodes.Package)
        )

        candidates.forEach {
            result.addElement(LookupElementBuilder.create(it))
        }
    }
}
