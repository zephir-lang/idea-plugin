/**
 * Copyright (c) 2014-2019 Zephir Team
 *
 * This source file is subject the MIT license, that is bundled with
 * this package in the file LICENSE, and is available through the
 * world-wide-web at the following url:
 *
 * https://github.com/zephir-lang/idea-plugin/blob/master/LICENSE
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
