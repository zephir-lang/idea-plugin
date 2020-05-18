// Copyright (c) 2014-2020 Zephir Team
//
// This source file is subject the MIT license, that is bundled with
// this package in the file LICENSE, and is available through the
// world-wide-web at the following url:
//
// https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE

package com.zephir.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.zephir.ZephirLanguage;
import com.zephir.completion.providers.ClassScopeKeywordsProvider;
import com.zephir.completion.providers.FileScopeKeywordsProvider;
import com.zephir.completion.providers.MethodScopeCompletionProvider;

public class ZephirKeywordCompletionContributor extends CompletionContributor {

    public ZephirKeywordCompletionContributor() {
        try {
            extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new FileScopeKeywordsProvider()
            );
        } catch (Exception e) {
            // @todo show message to developer and request him about report bug
        }

        try{
            extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new MethodScopeCompletionProvider()
            );
        } catch (Exception e) {
            // @todo show message to developer and request him about report bug
        }

        try {
            extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
                new ClassScopeKeywordsProvider()
            );
        } catch (Exception e) {
            // @todo show message to developer and request him about report bug
        }
    }


}
