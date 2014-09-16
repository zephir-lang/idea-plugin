package com.zephir.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.zephir.ZephirLanguage;
import com.zephir.psi.ZephirTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Nikita Gusakov
 */
public class ZephirClassCompletion extends CompletionContributor {
    public ZephirClassCompletion() {
        extend(
                CompletionType.CLASS_NAME,
                PlatformPatterns.psiElement(ZephirTypes.IDENTIFIER).withLanguage(ZephirLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        resultSet.addElement(LookupElementBuilder.create("Hello"));
                    }
                }
        );
    }
}
