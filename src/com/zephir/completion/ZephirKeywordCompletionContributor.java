/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.completion;

import com.intellij.codeInsight.completion.*;
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
