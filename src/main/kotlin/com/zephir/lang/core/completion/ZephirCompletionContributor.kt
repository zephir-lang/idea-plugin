// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.zephir.lang.core.completion.providers.*

class ZephirCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, ZephirClassScopeKeywordsProvider)
        extend(CompletionType.BASIC, ZephirFileScopeKeywordsProvider)
        extend(CompletionType.BASIC, ZephirMethodScopeCompletionProvider)
    }

    private fun extend(type: CompletionType?, provider:  ZephirCompletionProvider) {
        extend(type, provider.elementPattern, provider)
    }
}
