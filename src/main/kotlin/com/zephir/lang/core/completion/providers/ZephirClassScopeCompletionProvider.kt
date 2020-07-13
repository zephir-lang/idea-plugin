package com.zephir.lang.core.completion.providers

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.util.PlatformIcons
import com.intellij.util.ProcessingContext
import com.zephir.lang.core.ZephirLanguage
import com.zephir.lang.core.completion.ZephirCompletionProvider
import com.zephir.lang.core.psi.ZephirClassBody

/**
 * Provides code completion support for the class scope.
 */
object ZephirClassScopeCompletionProvider : ZephirCompletionProvider() {
    private val keywordsWithIcons = mapOf(
        // TODO(serghei): Move "function" to keywords after implementing snippets
        "function" to PlatformIcons.FUNCTION_ICON
    )

    private val keywords = arrayOf(
        "protected",
        "public",
        "private",
        "static",
        "inline",
        "internal",
        "final",
        "abstract"
    )

    override val context: ElementPattern<out PsiElement>
        get() = psiElement().withLanguage(ZephirLanguage)

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val originalPosition = parameters.originalPosition
        val parent = originalPosition?.parent ?: return

        if (parent is ZephirClassBody) {
            // TODO(serghei): Move "function" to keywords after implementing snippets
            keywordsWithIcons.forEach {
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
}
