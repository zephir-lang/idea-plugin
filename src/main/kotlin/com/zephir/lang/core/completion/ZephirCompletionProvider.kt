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
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.util.ProcessingContext
import com.zephir.lang.core.completion.suggestors.*

// TODO(serghei): re-visit this later
class ZephirCompletionProvider : CompletionProvider<CompletionParameters>() {
    private val suggestors = listOf(
        ZephirFileScopeKeywordsSuggestor,
        ZephirMethodScopeCompletionSuggestor,
        ZephirClassScopeKeywordsSuggestor
    )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        suggestors.forEach {
            it.addCompletions(parameters, result)
        }
    }
}
