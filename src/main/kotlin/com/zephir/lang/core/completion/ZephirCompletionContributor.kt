// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.lang.core.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.zephir.lang.core.completion.providers.ZephirClassScopeCompletionProvider
import com.zephir.lang.core.completion.providers.ZephirFileScopeCompletionProvider

class ZephirCompletionContributor : CompletionContributor() {
    private val providers = listOf(
        ZephirFileScopeCompletionProvider,
        ZephirClassScopeCompletionProvider
    )

    init {
        providers.forEach { extend(it) }
    }

    private fun extend(provider: ZephirCompletionProvider) {
        extend(provider.type, provider.context, provider)
    }
}
