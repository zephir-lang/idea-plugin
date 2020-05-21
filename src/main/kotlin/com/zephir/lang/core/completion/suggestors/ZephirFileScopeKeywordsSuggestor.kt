// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion.suggestors

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirFile

object ZephirFileScopeKeywordsSuggestor : ZephirCompletionSuggestor {
    private val keywords = arrayOf(
        "internal",
        "inline",
        "namespace",
        "use",
        "as",
        "interface",
        "class",
        "extends",
        "implements",
        "final",
        "abstract"
    )

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val psiElement = parameters.originalPosition
        val parent = psiElement?.parent ?: return

        if (parent is ZephirFile) {
            for (keyword in keywords) {
                result.addElement(LookupElementBuilder.create(keyword))
            }
        }
    }
}
