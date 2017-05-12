package com.zephir.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.zephir.psi.ZephirClassBody;
import org.jetbrains.annotations.NotNull;

public class ClassScopeKeywordsProvider extends CompletionProvider<CompletionParameters> {
    private String[] keywords = new String[] {
        "function", "protected", "public", "private", "static", "inline", "final", "abstract"
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
        if(!(parent instanceof ZephirClassBody)) {
            return;
        }

        for (String keyword : this.keywords) {
            result.addElement(LookupElementBuilder.create(keyword));
        }
    }
}
