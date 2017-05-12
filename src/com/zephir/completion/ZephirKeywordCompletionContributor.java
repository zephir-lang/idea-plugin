package com.zephir.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.zephir.ZephirLanguage;
import com.zephir.completion.providers.ClassScopeKeywordsProvider;
import com.zephir.completion.providers.FileScopeKeywordsProvider;
import com.zephir.completion.providers.MethodScopeCompletionProvider;

/**
 * @contributors
 *  Nikita Gusakov
 *  Alex Baranezky
 */
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
