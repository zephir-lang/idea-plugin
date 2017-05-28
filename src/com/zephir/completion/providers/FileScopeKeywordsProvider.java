/**
 * Copyright (c) 2014-2017 Phalcon Team
 *
 * Licensed under the MIT License (MIT);
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://github.com/phalcon/zephir-idea-plugin/blob/master/LICENSE
 */

package com.zephir.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.zephir.psi.ZephirFile;
import org.jetbrains.annotations.NotNull;

public class FileScopeKeywordsProvider extends CompletionProvider<CompletionParameters> {
    private String[] keywords = new String[] {
        "internal", "inline", "namespace", "use", "as", "interface", "class", "extends", "implements", "final", "abstract"
    };

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  ProcessingContext context,
                                  @NotNull CompletionResultSet result) {

        PsiElement psiElement = parameters.getOriginalPosition();
        if(psiElement == null) {
            return;
        }

        PsiElement parent = psiElement.getParent();
        if(!(parent instanceof ZephirFile)) {
            return;
        }

        for (String keyword : this.keywords) {
            result.addElement(LookupElementBuilder.create(keyword));
        }
    }
}
