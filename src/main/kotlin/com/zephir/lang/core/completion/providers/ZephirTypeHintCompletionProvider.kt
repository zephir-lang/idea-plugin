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
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import com.zephir.lang.core.ZephirLanguage
import com.zephir.lang.core.completion.ZephirCompletionProvider
import com.zephir.lang.core.psi.ZephirMethodDefinition
import com.zephir.lang.core.psi.ZephirTypes

/**
 * Provides code completion support for zephir type hints.
 */
object ZephirTypeHintCompletionProvider : ZephirCompletionProvider() {
    private val candidates = arrayOf(
        "void",
        "null",
        "int",
        "uint",
        "long",
        "ulong",
        "char",
        "double",
        "bool",
        "string",
        "array",
        "var",
        "callable",
        "resource",
        "object"
    )

    override val context: ElementPattern<out PsiElement>
        get() = psiElement().withLanguage(ZephirLanguage)

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        if (isSuitableContext(parameters)) {
            candidates.forEach {
                result.addElement(LookupElementBuilder
                    .create(it)
                    .withIcon(AllIcons.Nodes.Type)
                )
            }
        }
    }

    private fun isSuitableContext(parameters: CompletionParameters): Boolean {
        val originalPosition = parameters.originalPosition
        val parent = originalPosition?.parent ?: return false

        if (parent is ZephirMethodDefinition) {
            return when {
                //
                // 1. public function foo() -> T | X {}
                //    ----------------------------^^^
                //
                // 2. public function foo() -> X {}
                //    ------------------------^^^
                //
                originalPosition.nextSibling.elementType == ZephirTypes.METHOD_BODY &&
                        originalPosition.prevSibling.elementType == ZephirTypes.RETURN_TYPE -> true
                else -> false
            }
        }

        return false
    }
}
