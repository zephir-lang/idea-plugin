package com.zephir.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.zephir.ZephirLanguage;
import com.zephir.completion.providers.FileScopeKeywordsProvider;
import com.zephir.completion.providers.MethodScopeComplectionProvider;

/**
 * @contributors
 *  Nikita Gusakov
 *  Alex Baranezky
 */
public class ZephirKeywordCompletionContributor extends CompletionContributor {
    public ZephirKeywordCompletionContributor() {

        extend(CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
            new FileScopeKeywordsProvider()
        );

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withLanguage(ZephirLanguage.INSTANCE),
            new MethodScopeComplectionProvider()
        );
    }


}
