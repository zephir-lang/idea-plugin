// Copyright (c) 2014-2026 Zephir Team
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
import com.intellij.psi.util.PsiTreeUtil
import com.zephir.lang.core.completion.ZephirCompletionScope
import com.zephir.lang.core.completion.ZephirCompletionSuggestor
import com.zephir.lang.core.psi.ZephirClassDefinition
import com.zephir.lang.core.psi.ZephirInterfaceDefinition

/** Offers `extends` and `implements` after a class name and `extends` after an interface name. */
object ZephirHeaderKeywordsSuggestor : ZephirCompletionSuggestor {

    override fun addCompletions(parameters: CompletionParameters, result: CompletionResultSet) {
        val position = parameters.originalPosition
        if (ZephirCompletionScope.of(position) != ZephirCompletionScope.HEADER) return

        val keywords = when {
            PsiTreeUtil.getParentOfType(position, ZephirClassDefinition::class.java) != null ->
                listOf("extends", "implements")
            PsiTreeUtil.getParentOfType(position, ZephirInterfaceDefinition::class.java) != null ->
                listOf("extends")
            else -> emptyList()
        }
        for (keyword in keywords) {
            result.addElement(LookupElementBuilder.create(keyword))
        }
    }
}
